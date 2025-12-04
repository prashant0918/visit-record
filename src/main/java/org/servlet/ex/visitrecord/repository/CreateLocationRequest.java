package org.servlet.ex.visitrecord.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLocationRequest {
    private String addressLine;
    private double latitude;
    private double longitude;
}
