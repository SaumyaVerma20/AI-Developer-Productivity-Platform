package com.aiops.ai_analysis_service.service;

import com.aiops.ai_analysis_service.dto.AIAnalysisCompletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AIAnalysisEventProducer {

    private static final String TOPIC =
            "ai-analysis-events";

    private final KafkaTemplate<
            String,
            AIAnalysisCompletedEvent> kafkaTemplate;

    public AIAnalysisEventProducer(
            KafkaTemplate<
                    String,
                    AIAnalysisCompletedEvent> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(
            AIAnalysisCompletedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event);
    }
}