package org.servlet.ex.visitrecord.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateVisitRequest {

    @NotNull
    private Long salesmanId;

    @NotNull
    private Long locationId;

    @NotNull
    private LocalDate scheduledDate;

    @NotNull
    private Integer priority;
}

