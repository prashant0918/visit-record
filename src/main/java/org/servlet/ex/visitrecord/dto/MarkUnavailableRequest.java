package org.servlet.ex.visitrecord.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkUnavailableRequest {

    @NotBlank
    private String reason;
}

