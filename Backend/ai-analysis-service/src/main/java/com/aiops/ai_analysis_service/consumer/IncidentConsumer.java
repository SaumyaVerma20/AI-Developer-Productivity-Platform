package com.aiops.ai_analysis_service.consumer;

import com.aiops.ai_analysis_service.dto.IncidentEvent;
import com.aiops.ai_analysis_service.service.AIAnalysisService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import com.aiops.ai_analysis_service.service.EmbeddingService;

@Component
public class IncidentConsumer {

    private final AIAnalysisService
            aiAnalysisService;
        private final EmbeddingService embeddingService;

    public IncidentConsumer(
            AIAnalysisService aiAnalysisService,
            EmbeddingService embeddingService) {

        this.aiAnalysisService =
                aiAnalysisService;
        this.embeddingService = embeddingService;
    }

   @KafkaListener(
        topics = "incident-events",
        containerFactory = "kafkaListenerContainerFactory")
public void consume(IncidentEvent event) {

    System.out.println(
            "Received incident : "
                    + event.getTitle());

    embeddingService.storeIncident(event);

    aiAnalysisService.analyzeIncident(event);
}
}