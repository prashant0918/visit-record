package org.servlet.ex.visitrecord.dto;

import lombok.Builder;
import lombok.Getter;
import org.servlet.ex.visitrecord.domain.VisitPriority;
import org.servlet.ex.visitrecord.domain.VisitStatus;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Builder
public class VisitResponse {

    private Long id;
    private Long salesmanId;
    private Long locationId;
    private LocalDate scheduledDate;
    private VisitPriority priority;
    private VisitStatus status;
    private Instant arrivalTime;
    private Instant departureTime;
    private String skipReason;
}

