package org.servlet.ex.visitrecord.service;

import lombok.RequiredArgsConstructor;
import org.servlet.ex.visitrecord.domain.*;
import org.servlet.ex.visitrecord.dto.CreateVisitRequest;
import org.servlet.ex.visitrecord.dto.VisitResponse;
import org.servlet.ex.visitrecord.repository.LocationRepository;
import org.servlet.ex.visitrecord.repository.SalesmanRepository;
import org.servlet.ex.visitrecord.repository.VisitRepository;
import org.servlet.ex.visitrecord.repository.VisitStatusHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final SalesmanRepository salesmanRepository;
    private final LocationRepository locationRepository;
    private final VisitStatusHistoryRepository historyRepository;
    private final NotificationService notificationService;

    // ----- helper -----

    private void recordStatusChange(Visit visit, VisitStatus from, VisitStatus to,
                                    ActorType actorType, Long actorId, String reason) {
        VisitStatusHistory h = new VisitStatusHistory();
        h.setVisit(visit);
        h.setFromStatus(from);
        h.setToStatus(to);
        h.setChangedAt(Instant.now());
        h.setActorType(actorType);
        h.setActorId(actorId);
        h.setReason(reason);
        historyRepository.save(h);
    }

    private VisitResponse toDto(Visit v) {
        return VisitResponse.builder()
                .id(v.getId())
                .salesmanId(v.getSalesman().getId())
                .locationId(v.getLocation().getId())
                .scheduledDate(v.getScheduledDate())
                .priority(v.getPriority())
                .status(v.getStatus())
                .arrivalTime(v.getArrivalTime())
                .departureTime(v.getDepartureTime())
                .skipReason(v.getSkipReason())
                .build();
    }

    // ----- ADMIN SIDE -----

    @Transactional
    public VisitResponse createVisit(CreateVisitRequest req) {
        Salesman s = salesmanRepository.findById(req.getSalesmanId())
                .orElseThrow(() -> new IllegalArgumentException("Salesman not found"));
        Location location = locationRepository.findById(req.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));

        Visit visit = new Visit();
        visit.setSalesman(s);
        visit.setLocation(location);
        visit.setPriority(req.getPriority());
        visit.setScheduledDate(req.getScheduledDate());
        visit.setStatus(VisitStatus.ASSIGNED);

        Visit saved = visitRepository.save(visit);
        recordStatusChange(saved, null, VisitStatus.ASSIGNED, ActorType.ADMIN, null,
                "Assigned via admin create");
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<VisitResponse> getScheduleForSalesman(Long salesmanId, LocalDate date) {
        return visitRepository
                .findBySalesmanIdAndScheduledDateOrderByPriorityDesc(salesmanId, date)
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    // ----- SALESMAN SIDE -----

    @Transactional(readOnly = true)
    public VisitResponse getNextVisitForSalesman(Long salesmanId, LocalDate date) {
        return visitRepository
                .findFirstBySalesmanIdAndScheduledDateAndStatusInOrderByPriorityDesc(
                        salesmanId,
                        date,
                        List.of(VisitStatus.ASSIGNED)
                )
                .map(this::toDto)
                .orElse(null);
    }

    @Transactional
    public VisitResponse startVisitManual(Long visitId, Long salesmanId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found"));

        if (!visit.getSalesman().getId().equals(salesmanId)) {
            throw new IllegalStateException("Visit does not belong to this salesman");
        }

        VisitStatus old = visit.getStatus();
        visit.setStatus(VisitStatus.IN_PROGRESS);
        visit.setArrivalTime(Instant.now());

        recordStatusChange(visit, old, VisitStatus.IN_PROGRESS,
                ActorType.SALESMAN, salesmanId, "Salesman started visit (manual)");

        return toDto(visit);
    }

    @Transactional
    public VisitResponse completeVisit(Long visitId, Long salesmanId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found"));

        if (!visit.getSalesman().getId().equals(salesmanId)) {
            throw new IllegalStateException("Visit does not belong to this salesman");
        }

        VisitStatus from = visit.getStatus();
        visit.setStatus(VisitStatus.COMPLETED);
        if (visit.getDepartureTime() == null) {
            visit.setDepartureTime(Instant.now());
        }

        recordStatusChange(visit, from, VisitStatus.COMPLETED,
                ActorType.SALESMAN, salesmanId, "Visit completed");

        notificationService.notifyAdmin(
                null,
                NotificationType.VISIT_COMPLETED,
                "Visit " + visit.getId() + " completed by salesman " + salesmanId
        );

        return toDto(visit);
    }

    @Transactional
    public VisitResponse markUnavailableAndSkip(Long visitId, Long salesmanId, String reason) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found"));

        if (!visit.getSalesman().getId().equals(salesmanId)) {
            throw new IllegalStateException("Visit does not belong to this salesman");
        }

        VisitStatus from = visit.getStatus();
        visit.setStatus(VisitStatus.UNAVAILABLE);
        visit.setSkipReason(reason);
        recordStatusChange(visit, from, VisitStatus.UNAVAILABLE,
                ActorType.SALESMAN, salesmanId, reason);

        notificationService.notifyAdmin(
                null,
                NotificationType.VISIT_UNAVAILABLE,
                "Visit " + visit.getId() + " unavailable: " + reason
        );

        VisitStatus prev = visit.getStatus();
        visit.setStatus(VisitStatus.SKIPPED);
        recordStatusChange(visit, prev, VisitStatus.SKIPPED,
                ActorType.SYSTEM, null, "Auto-skipped after unavailability");

        return toDto(visit);
    }

    // ----- Geofence integration -----

    @Transactional
    public void markArrivedByGeofence(Visit visit, Instant at) {
        VisitStatus from = visit.getStatus();
        if (visit.getArrivalTime() == null) {
            visit.setArrivalTime(at != null ? at : Instant.now());
        }
        visit.setStatus(VisitStatus.IN_PROGRESS);
        recordStatusChange(visit, from, VisitStatus.IN_PROGRESS,
                ActorType.SYSTEM, null, "Auto-arrival via geofence");
    }

    @Transactional
    public void markDepartedByGeofence(Visit visit, Instant at) {
        VisitStatus from = visit.getStatus();
        if (visit.getDepartureTime() == null) {
            visit.setDepartureTime(at != null ? at : Instant.now());
        }
        visit.setStatus(VisitStatus.COMPLETED);
        recordStatusChange(visit, from, VisitStatus.COMPLETED,
                ActorType.SYSTEM, null, "Auto-departure via geofence");
    }
}

