package com.aiops.ai_analysis_service.service;

import com.aiops.ai_analysis_service.dto.IncidentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmbeddingService {

    private final VectorStore vectorStore;

    public void storeIncident(IncidentEvent incident) {

        String document = buildDocument(incident);

        vectorStore.add(List.of(
                new Document(
                        document,
                        Map.of(
                                "incidentId", incident.getIncidentId(),
                                "severity", incident.getSeverity()
                        )
                )
        ));
    }

    private String buildDocument(IncidentEvent incident) {

        return """
                Incident Id : %d

                Title : %s

                Description : %s

                Severity : %s

                Created At : %s
                """
                .formatted(
                        incident.getIncidentId(),
                        incident.getTitle(),
                        incident.getDescription(),
                        incident.getSeverity(),
                        incident.getCreatedAt()
                );
    }
}