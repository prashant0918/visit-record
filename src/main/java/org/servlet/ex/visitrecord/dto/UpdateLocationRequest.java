package org.servlet.ex.visitrecord.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateLocationRequest {
    private String addressLine;
    private Double latitude;
    private Double longitude;
}
