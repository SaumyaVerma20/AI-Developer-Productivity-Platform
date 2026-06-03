package com.aiops.notification_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationResponse {

    private Long id;

    private Long incidentId;

    private String message;

    private String status;

    private LocalDateTime sentAt;
}