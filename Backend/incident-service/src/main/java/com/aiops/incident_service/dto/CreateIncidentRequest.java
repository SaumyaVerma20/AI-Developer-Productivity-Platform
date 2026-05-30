package com.aiops.incident_service.dto;

import com.aiops.incident_service.entity.Severity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateIncidentRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Severity severity;
}