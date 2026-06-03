package com.aiops.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class IncidentEventConsumer {

    @KafkaListener(
            topics = "incident-events",
            groupId = "notification-group"
    )
    public void consume(String message){

        System.out.println("========== EVENT RECEIVED ==========");
        System.out.println(message);
        System.out.println("====================================");

    }
}