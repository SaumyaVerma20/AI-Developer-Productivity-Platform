package com.aiops.notification_service.consumer;

import com.aiops.notification_service.dto.AIAnalysisCompletedEvent;
import com.aiops.notification_service.dto.NotificationEvent;
import com.aiops.notification_service.service.NotificationService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AIAnalysisEventConsumer {

    private final NotificationService notificationService;

    public AIAnalysisEventConsumer(
            NotificationService notificationService) {

        this.notificationService =
                notificationService;
    }

    @KafkaListener(
            topics = "ai-analysis-events",
            groupId = "notification-group-v2")
    public void consume(
            AIAnalysisCompletedEvent event) {

        System.out.println(
                "Received AI Analysis for Incident: "
                        + event.getTitle());

        NotificationEvent notificationEvent =
                new NotificationEvent();

        notificationEvent.setIncidentId(
                event.getIncidentId());

        notificationEvent.setSeverity(
                event.getSeverity());

        notificationEvent.setMessage(
                """
                Incident: %s

                Root Cause:
                %s

                Recommendation:
                %s
                """
                        .formatted(
                                event.getTitle(),
                                event.getRootCause(),
                                event.getRecommendation()));

        notificationService
                .processNotification(
                        notificationEvent);
    }
}