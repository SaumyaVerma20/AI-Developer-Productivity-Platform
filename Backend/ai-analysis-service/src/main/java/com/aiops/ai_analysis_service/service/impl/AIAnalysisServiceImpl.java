package com.aiops.ai_analysis_service.service.impl;

import com.aiops.ai_analysis_service.dto.AIAnalysisResponse;
import com.aiops.ai_analysis_service.dto.IncidentEvent;
import com.aiops.ai_analysis_service.entity.AIAnalysis;
import com.aiops.ai_analysis_service.repository.AIAnalysisRepository;
import com.aiops.ai_analysis_service.service.AIAnalysisService;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AIAnalysisServiceImpl
        implements AIAnalysisService {

    private final AIAnalysisRepository
            aiAnalysisRepository;

    private final ChatClient
            chatClient;

    public AIAnalysisServiceImpl(
            AIAnalysisRepository aiAnalysisRepository,
            ChatClient chatClient) {

        this.aiAnalysisRepository =
                aiAnalysisRepository;

        this.chatClient =
                chatClient;
    }

    @Override
    public AIAnalysisResponse
    analyzeIncident(
            IncidentEvent event) {

        String prompt =
                """
                Analyze this production incident:

                Title: %s
                Description: %s
                Severity: %s

                Return in this exact format:

                Root Cause:
                <root cause>

                Recommendation:
                <recommendation>
                """
                .formatted(
                        event.getTitle(),
                        event.getDescription(),
                        event.getSeverity()
                );

        String aiResponse =
                chatClient
                        .prompt(prompt)
                        .call()
                        .content();

        String rootCause =
                extractSection(
                        aiResponse,
                        "Root Cause:",
                        "Recommendation:"
                );

        String recommendation =
                extractSection(
                        aiResponse,
                        "Recommendation:",
                        null
                );

        AIAnalysis analysis =
                new AIAnalysis();

        analysis.setIncidentId(
                event.getIncidentId());

        analysis.setIncidentTitle(
                event.getTitle());

        analysis.setIncidentDescription(
                event.getDescription());

        analysis.setSeverity(
                event.getSeverity().toString());

        analysis.setRootCause(
                rootCause);

        analysis.setRecommendation(
                recommendation);

        analysis.setAnalyzedAt(
                LocalDateTime.now());

        AIAnalysis saved =
                aiAnalysisRepository
                        .save(analysis);

        AIAnalysisResponse response =
                new AIAnalysisResponse();

        response.setIncidentId(
                saved.getIncidentId());

        response.setRootCause(
                saved.getRootCause());

        response.setRecommendation(
                saved.getRecommendation());

        return response;
    }

    private String extractSection(
            String text,
            String start,
            String end) {

        int startIndex =
                text.indexOf(start);

        if (startIndex == -1) {
            return "";
        }

        startIndex += start.length();

        if (end == null) {

            return text.substring(
                    startIndex)
                    .trim();
        }

        int endIndex =
                text.indexOf(
                        end,
                        startIndex);

        if (endIndex == -1) {

            return text.substring(
                    startIndex)
                    .trim();
        }

        return text.substring(
                startIndex,
                endIndex)
                .trim();
    }
}