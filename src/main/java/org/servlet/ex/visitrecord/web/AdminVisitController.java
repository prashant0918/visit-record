package org.servlet.ex.visitrecord.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.servlet.ex.visitrecord.domain.Visit;
import org.servlet.ex.visitrecord.dto.CreateVisitRequest;
import org.servlet.ex.visitrecord.dto.UpdateVisitRequest;
import org.servlet.ex.visitrecord.dto.VisitResponse;
import org.servlet.ex.visitrecord.repository.VisitRepository;
import org.servlet.ex.visitrecord.service.VisitService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminVisitController {

    private final VisitRepository visitRepository;
    private final VisitService visitService;

    private VisitResponse toDto(Visit v) {
        if (v == null) return null;
        Long salesmanId = v.getSalesman() != null ? v.getSalesman().getId() : null;
        Long locationId = v.getLocation() != null ? v.getLocation().getId() : null;

        return VisitResponse.builder()
                .id(v.getId())
                .salesmanId(salesmanId)
                .locationId(locationId)
                .scheduledDate(v.getScheduledDate())
                .priority(v.getPriority())
                .status(v.getStatus())
                .arrivalTime(v.getArrivalTime())
                .departureTime(v.getDepartureTime())
                .skipReason(v.getSkipReason())
                .build();
    }

    @PostMapping("/visits")
    public ResponseEntity<VisitResponse> createVisit(@Valid @RequestBody CreateVisitRequest request) {
        VisitResponse created = visitService.createVisit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/visits/{id}")
    public ResponseEntity<VisitResponse> getVisitById(@PathVariable Long id) {
        return visitRepository.findById(id)
                .map(v -> ResponseEntity.ok(toDto(v)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/visits/{id}")
    public ResponseEntity<VisitResponse> updateVisit(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVisitRequest req) {

        VisitResponse updated = visitService.updateVisit(id, req);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/visits/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        if (!visitRepository.existsById(id)) return ResponseEntity.notFound().build();
        visitRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/salesmen/{salesmanId}/schedule")
    public List<VisitResponse> getSchedule(
            @PathVariable Long salesmanId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return visitService.getScheduleForSalesman(salesmanId, date);
    }
}


