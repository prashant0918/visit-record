package org.servlet.ex.visitrecord.dto;

import lombok.Getter;
import lombok.Setter;
import org.servlet.ex.visitrecord.domain.VisitPriority;
import org.servlet.ex.visitrecord.domain.VisitStatus;

import java.time.LocalDate;

@Getter @Setter
public class UpdateVisitRequest {
    private Long salesmanId;    // to reassign
    private Long locationId;    // to change customer
    private LocalDate scheduledDate;
    private VisitPriority priority;
    private VisitStatus status; // allow admin to force status change if needed
}
