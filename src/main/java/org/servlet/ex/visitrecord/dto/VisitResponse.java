package org.servlet.ex.visitrecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant arrivalTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant departureTime;
    private String skipReason;
}

