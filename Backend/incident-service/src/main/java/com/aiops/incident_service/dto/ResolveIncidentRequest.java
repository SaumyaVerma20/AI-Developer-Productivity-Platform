package com.aiops.incident_service.dto;

import com.aiops.incident_service.entity.IncidentStatus;
import lombok.Data;

@Data
public class ResolveIncidentRequest {

    private IncidentStatus status;
}