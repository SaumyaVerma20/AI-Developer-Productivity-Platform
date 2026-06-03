package com.aiops.notification_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {

    private Long incidentId;

    private String message;

    private String severity;

    private String recipient;
}