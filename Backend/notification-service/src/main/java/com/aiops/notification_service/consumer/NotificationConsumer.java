package com.aiops.notification_service.consumer;

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
            topics = "notifications.send",
            groupId = "notification-group")
    public void consume(
            NotificationEvent event) {

        notificationService
                .processNotification(event);
    }
}