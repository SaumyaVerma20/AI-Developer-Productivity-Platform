package com.aiops.notification_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncidentEvent {

    private Long incidentId;

    private String message;
}