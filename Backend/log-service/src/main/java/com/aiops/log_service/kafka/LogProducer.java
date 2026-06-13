package com.aiops.log_service.kafka;

import com.aiops.log_service.dto.LogEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogProducer {

    private final KafkaTemplate<String, LogEvent> kafkaTemplate;

    public LogProducer(KafkaTemplate<String, LogEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishLog(LogEvent event) {
        kafkaTemplate.send("log-events", event);
    }
}
