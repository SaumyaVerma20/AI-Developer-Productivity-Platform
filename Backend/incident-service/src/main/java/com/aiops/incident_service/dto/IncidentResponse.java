package com.aiops.incident_service.dto;

import com.aiops.incident_service.entity.IncidentStatus;
import com.aiops.incident_service.entity.Severity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class IncidentResponse {

    private Long id;

    private String title;

    private String description;

    private Severity severity;

    private IncidentStatus status;

    private String createdBy;

    private LocalDateTime createdAt;
}