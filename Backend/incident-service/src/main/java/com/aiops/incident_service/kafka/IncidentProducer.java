package com.aiops.incident_service.kafka;

import com.aiops.incident_service.dto.IncidentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class IncidentProducer {

    private final KafkaTemplate<String, IncidentEvent> kafkaTemplate;

    public IncidentProducer(
            KafkaTemplate<String, IncidentEvent> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishIncident(
            IncidentEvent incidentEvent) {

        kafkaTemplate.send(
                "incident-events",
                incidentEvent
        );
    }
}