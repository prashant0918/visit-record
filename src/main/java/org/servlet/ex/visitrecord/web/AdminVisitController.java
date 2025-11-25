package org.servlet.ex.visitrecord.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.servlet.ex.visitrecord.dto.CreateVisitRequest;
import org.servlet.ex.visitrecord.dto.VisitResponse;
import org.servlet.ex.visitrecord.service.VisitService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminVisitController {

    private final VisitService visitService;

    @PostMapping("/visits")
    public VisitResponse createVisit(@Valid @RequestBody CreateVisitRequest request) {
        return visitService.createVisit(request);
    }

    @GetMapping("/salesmen/{salesmanId}/schedule")
    public List<VisitResponse> getSchedule(
            @PathVariable Long salesmanId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return visitService.getScheduleForSalesman(salesmanId, date);
    }
}

