package org.servlet.ex.visitrecord.service;

import lombok.RequiredArgsConstructor;
import org.servlet.ex.visitrecord.domain.Location;
import org.servlet.ex.visitrecord.domain.Visit;
import org.servlet.ex.visitrecord.domain.VisitStatus;
import org.servlet.ex.visitrecord.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeofenceService {

    private final VisitRepository visitRepository;
    private final VisitService visitService;

    @Value("${app.geofence.radiusMeters:100}")
    private double radiusMeters;

    @Value("${app.geofence.departureBufferMeters:150}")
    private double departureBufferMeters;

    public void handleLocationUpdate(Long salesmanId,
                                     double lat,
                                     double lon,
                                     Instant reportedAt) {

        LocalDate today = LocalDate.now(); // or derive from reportedAt

        Visit visit = visitRepository
                .findFirstBySalesmanIdAndScheduledDateAndStatusInOrderByPriorityDesc(
                        salesmanId,
                        today,
                        List.of(VisitStatus.ASSIGNED, VisitStatus.IN_PROGRESS)
                )
                .orElse(null);

        if (visit == null) {
            return; // no active visit
        }

        Location loc = visit.getLocation();
        if (loc.getLatitude() == null || loc.getLongitude() == null) {
            return;
        }

        double distance = haversineMeters(
                lat, lon,
                loc.getLatitude(), loc.getLongitude()
        );

        // ARRIVAL
        if (visit.getArrivalTime() == null && distance <= radiusMeters) {
            visitService.markArrivedByGeofence(visit, reportedAt);
            return;
        }

        // DEPARTURE
        if (visit.getArrivalTime() != null
                && visit.getDepartureTime() == null
                && distance > departureBufferMeters) {
            visitService.markDepartedByGeofence(visit, reportedAt);
        }
    }

    // Haversine is fine for small-radius geofence checks
    private double haversineMeters(double lat1, double lon1,
                                   double lat2, double lon2) {
        final double R = 6371000.0; // meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}

