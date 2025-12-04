package org.servlet.ex.visitrecord.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.servlet.ex.visitrecord.domain.SalesmanStatus;

@Getter
@Setter
public class CreateSalesmanRequest {
    @NotBlank
    private String name;

    private String phone;

    private String email;

    private SalesmanStatus status;

}

