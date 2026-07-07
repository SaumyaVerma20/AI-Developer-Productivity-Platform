package com.aiops.ai_analysis_service.service.impl;

import com.aiops.ai_analysis_service.dto.AIAnalysisCompletedEvent;
import com.aiops.ai_analysis_service.dto.AIAnalysisResponse;
import com.aiops.ai_analysis_service.dto.IncidentEvent;
import com.aiops.ai_analysis_service.entity.AIAnalysis;
import com.aiops.ai_analysis_service.repository.AIAnalysisRepository;
import com.aiops.ai_analysis_service.service.AIAnalysisEventProducer;
import com.aiops.ai_analysis_service.service.AIAnalysisService;
import com.aiops.ai_analysis_service.service.EmbeddingService;
import com.aiops.ai_analysis_service.service.SimilarIncidentService;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AIAnalysisServiceImpl implements AIAnalysisService {

    private final AIAnalysisRepository aiAnalysisRepository;

    private final ChatClient chatClient;

    private final AIAnalysisEventProducer eventProducer;

    private final SimilarIncidentService similarIncidentService;

    private final EmbeddingService embeddingService;

    public AIAnalysisServiceImpl(
            AIAnalysisRepository aiAnalysisRepository,
            ChatClient chatClient,
            AIAnalysisEventProducer eventProducer,
            SimilarIncidentService similarIncidentService,
            EmbeddingService embeddingService) {

        this.aiAnalysisRepository = aiAnalysisRepository;
        this.chatClient = chatClient;
        this.eventProducer = eventProducer;
        this.similarIncidentService = similarIncidentService;
        this.embeddingService = embeddingService;
    }

    @Override
    public AIAnalysisResponse analyzeIncident(IncidentEvent event) {

        String searchQuery =
                event.getTitle() + " " +
                event.getDescription() + " " +
                event.getSeverity();

        List<Document> similarIncidents =
                similarIncidentService.searchSimilarIncidents(searchQuery);

        StringBuilder context = new StringBuilder();

        if (!similarIncidents.isEmpty()) {

            context.append("""
                    Previous Similar Incidents:

                    """);

            for (Document doc : similarIncidents) {

                context.append(doc.getText())
                        .append("\n------------------------\n");
            }
        }

        String prompt =
                """
                You are a Senior Site Reliability Engineer.

                Use previous incidents only as reference.

                If they are relevant, reuse their knowledge.

                Otherwise ignore them.

                %s

                Current Incident

                Title: %s

                Description: %s

                Severity: %s

                Return exactly:

                Root Cause:
                <root cause>

                Recommendation:
                <recommendation>
                """
                .formatted(
                        context.toString(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getSeverity());

        String aiResponse =
                chatClient
                        .prompt(prompt)
                        .call()
                        .content();

        String rootCause =
                extractSection(
                        aiResponse,
                        "Root Cause:",
                        "Recommendation:");

        String recommendation =
                extractSection(
                        aiResponse,
                        "Recommendation:",
                        null);

        AIAnalysis analysis = new AIAnalysis();

        analysis.setIncidentId(event.getIncidentId());
        analysis.setIncidentTitle(event.getTitle());
        analysis.setIncidentDescription(event.getDescription());
        analysis.setSeverity(event.getSeverity());
        analysis.setRootCause(rootCause);
        analysis.setRecommendation(recommendation);
        analysis.setAnalyzedAt(LocalDateTime.now());

        AIAnalysis saved =
                aiAnalysisRepository.save(analysis);

        AIAnalysisCompletedEvent completedEvent =
                new AIAnalysisCompletedEvent();

        completedEvent.setIncidentId(saved.getIncidentId());
        completedEvent.setTitle(saved.getIncidentTitle());
        completedEvent.setSeverity(saved.getSeverity());
        completedEvent.setRootCause(saved.getRootCause());
        completedEvent.setRecommendation(saved.getRecommendation());

        eventProducer.publish(completedEvent);

        embeddingService.storeIncident(event);

        AIAnalysisResponse response =
                new AIAnalysisResponse();

        response.setIncidentId(saved.getIncidentId());
        response.setRootCause(saved.getRootCause());
        response.setRecommendation(saved.getRecommendation());

        return response;
    }

    private String extractSection(
            String text,
            String start,
            String end) {

        int startIndex = text.indexOf(start);

        if (startIndex == -1) {
            return "";
        }

        startIndex += start.length();

        if (end == null) {

            return text.substring(startIndex).trim();
        }

        int endIndex =
                text.indexOf(end, startIndex);

        if (endIndex == -1) {

            return text.substring(startIndex).trim();
        }

        return text.substring(startIndex, endIndex).trim();
    }
}