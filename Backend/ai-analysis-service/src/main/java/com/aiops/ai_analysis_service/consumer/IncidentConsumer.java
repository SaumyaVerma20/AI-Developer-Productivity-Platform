package com.aiops.ai_analysis_service.consumer;

import com.aiops.ai_analysis_service.dto.IncidentEvent;
import com.aiops.ai_analysis_service.service.AIAnalysisService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class IncidentConsumer {

    private final AIAnalysisService
            aiAnalysisService;

    public IncidentConsumer(
            AIAnalysisService aiAnalysisService) {

        this.aiAnalysisService =
                aiAnalysisService;
    }

    @KafkaListener(
            topics = "incident-events",
            groupId = "ai-analysis-group",
            containerFactory ="kafkaListenerContainerFactory")
    public void consume(
            IncidentEvent event) {

        System.out.println(
                "Received incident: "
                        + event.getTitle());

        aiAnalysisService
                .analyzeIncident(event);
    }
}