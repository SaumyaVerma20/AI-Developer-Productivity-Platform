package com.aiops.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.aiops.notification_service.dto.IncidentEvent;
import com.aiops.notification_service.dto.NotificationEvent;
import com.aiops.notification_service.service.NotificationService;

@Service
public class IncidentEventConsumer {

    private final NotificationService notificationService;

    public IncidentEventConsumer(
            NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "incident-events")
    public void consume(IncidentEvent event) {

        NotificationEvent notification =
                new NotificationEvent();

        notification.setIncidentId(
                event.getIncidentId());

        notification.setMessage(
        "New Incident Created: "
                + event.getTitle());

        notification.setSeverity(
                "HIGH");

        notification.setRecipient(
                "admin@company.com");

        notificationService
                .processNotification(notification);
    }
}