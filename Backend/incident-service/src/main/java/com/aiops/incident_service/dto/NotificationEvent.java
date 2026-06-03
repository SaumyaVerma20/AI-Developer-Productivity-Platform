package com.aiops.incident_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationEvent {

    private Long incidentId;
    private String message;
    private String severity;
    private String recipient;
}