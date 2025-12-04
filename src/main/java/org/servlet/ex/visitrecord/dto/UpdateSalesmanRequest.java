package org.servlet.ex.visitrecord.dto;

import lombok.Getter;
import lombok.Setter;
import org.servlet.ex.visitrecord.domain.SalesmanStatus;

@Getter @Setter
public class UpdateSalesmanRequest {
    private String name;
    private String phone;
    private String email;
    private SalesmanStatus status;
}
