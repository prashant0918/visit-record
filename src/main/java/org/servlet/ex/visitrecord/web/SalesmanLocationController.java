package org.servlet.ex.visitrecord.web;
import lombok.RequiredArgsConstructor;
import org.servlet.ex.visitrecord.dto.LocationUpdateRequest;
import org.servlet.ex.visitrecord.service.GeofenceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salesmen")
@RequiredArgsConstructor
public class SalesmanLocationController {

    private final GeofenceService geofenceService;

    @PostMapping("/{salesmanId}/location")
    public void updateLocation(@PathVariable Long salesmanId,
                               @RequestBody LocationUpdateRequest body) {
        geofenceService.handleLocationUpdate(
                salesmanId,
                body.getLatitude(),
                body.getLongitude(),
                body.getTimestamp()
        );
    }
}
