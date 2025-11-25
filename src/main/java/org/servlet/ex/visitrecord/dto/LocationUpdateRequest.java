package org.servlet.ex.visitrecord.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class LocationUpdateRequest {
    private double latitude;
    private double longitude;
    private Instant timestamp; // client timestamp; can be null
}

