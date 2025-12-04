package org.servlet.ex.visitrecord.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.servlet.ex.visitrecord.domain.Salesman;
import org.servlet.ex.visitrecord.dto.CreateSalesmanRequest;
import org.servlet.ex.visitrecord.dto.UpdateSalesmanRequest;
import org.servlet.ex.visitrecord.service.SalesmanService;
import org.servlet.ex.visitrecord.repository.SalesmanRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/salesmen")
@RequiredArgsConstructor
public class SalesmanAdminController {

    private final SalesmanService salesmanService;
    private final SalesmanRepository salesmanRepository;

    @PostMapping
    public ResponseEntity<Salesman> createSalesman(@Valid @RequestBody CreateSalesmanRequest request) {
        Salesman saved = salesmanService.create(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Salesman> listAll() {
        return salesmanRepository.findAll();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Salesman> updateSalesman(
            @PathVariable Long id,
            @RequestBody UpdateSalesmanRequest req) {

        return salesmanRepository.findById(id)
                .map(existing -> {
                    if (req.getName() != null) existing.setName(req.getName());
                    if (req.getPhone() != null) existing.setPhone(req.getPhone());
                    if (req.getEmail() != null) existing.setEmail(req.getEmail());
                    if (req.getStatus() != null) existing.setStatus(req.getStatus());
                    Salesman saved = salesmanRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesman(@PathVariable Long id) {
        if (!salesmanRepository.existsById(id)) return ResponseEntity.notFound().build();
        salesmanRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Salesman> getById(@PathVariable Long id) {
        return salesmanRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

