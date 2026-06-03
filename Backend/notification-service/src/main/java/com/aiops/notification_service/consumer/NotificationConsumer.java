package com.aiops.notification_service.consumer;

import com.aiops.notification_service.dto.IncidentEvent;
import com.aiops.notification_service.dto.NotificationEvent;
import com.aiops.notification_service.service.NotificationService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final NotificationService notificationService;

    public NotificationConsumer(
            NotificationService notificationService) {

        this.notificationService =
                notificationService;
    }

    @KafkaListener(
            topics = "incident-events",
            groupId = "notification-group")
    public void consume(
            IncidentEvent event) {

        System.out.println(
                "Received: "
                + event.getTitle());

        NotificationEvent notificationEvent =
                new NotificationEvent();

        notificationEvent.setIncidentId(
                event.getIncidentId());

        notificationEvent.setMessage(
                "New Incident Created: "
                + event.getTitle());

        notificationEvent.setSeverity(
                event.getSeverity());

        notificationService
                .processNotification(
                        notificationEvent);
    }
}