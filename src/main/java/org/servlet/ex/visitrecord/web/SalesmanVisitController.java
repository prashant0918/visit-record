package org.servlet.ex.visitrecord.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.servlet.ex.visitrecord.dto.MarkUnavailableRequest;
import org.servlet.ex.visitrecord.dto.VisitResponse;
import org.servlet.ex.visitrecord.service.VisitService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/salesmen")
@RequiredArgsConstructor
public class SalesmanVisitController {

    private final VisitService visitService;

    @GetMapping("/{salesmanId}/next-visit")
    public VisitResponse getNextVisit(
            @PathVariable Long salesmanId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return visitService.getNextVisitForSalesman(salesmanId, date);
    }

    // manual start endpoint (optional if you want explicit start)
    @PostMapping("/{salesmanId}/visits/{visitId}/start")
    public VisitResponse startVisit(@PathVariable Long salesmanId,
                                    @PathVariable Long visitId) {
        return visitService.startVisitManual(visitId, salesmanId);
    }

    @PostMapping("/{salesmanId}/visits/{visitId}/complete")
    public VisitResponse completeVisit(@PathVariable Long salesmanId,
                                       @PathVariable Long visitId) {
        return visitService.completeVisit(visitId, salesmanId);
    }

    @PostMapping("/{salesmanId}/visits/{visitId}/unavailable")
    public VisitResponse markUnavailable(@PathVariable Long salesmanId,
                                         @PathVariable Long visitId,
                                         @Valid @RequestBody MarkUnavailableRequest body) {
        return visitService.markUnavailableAndSkip(visitId, salesmanId, body.getReason());
    }
}

