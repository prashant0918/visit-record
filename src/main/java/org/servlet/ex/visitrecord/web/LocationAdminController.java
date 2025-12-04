package org.servlet.ex.visitrecord.web;

import lombok.RequiredArgsConstructor;
import org.servlet.ex.visitrecord.domain.Location;
import org.servlet.ex.visitrecord.dto.UpdateLocationRequest;
import org.servlet.ex.visitrecord.repository.CreateLocationRequest;
import org.servlet.ex.visitrecord.repository.LocationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/locations")
@RequiredArgsConstructor
public class LocationAdminController {

    private final LocationRepository locationRepository;

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody CreateLocationRequest req) {
        Location location = new Location();
        location.setAddressLine(req.getAddressLine());  // ✔ Correct field
        location.setLatitude(req.getLatitude());
        location.setLongitude(req.getLongitude());

        Location saved = locationRepository.save(location);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(
            @PathVariable Long id,
            @RequestBody UpdateLocationRequest req) {

        return locationRepository.findById(id)
                .map(existing -> {
                    if (req.getAddressLine() != null) existing.setAddressLine(req.getAddressLine());
                    if (req.getLatitude() != null) existing.setLatitude(req.getLatitude());
                    if (req.getLongitude() != null) existing.setLongitude(req.getLongitude());
                    Location saved = locationRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        if (!locationRepository.existsById(id)) return ResponseEntity.notFound().build();
        locationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        return locationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
