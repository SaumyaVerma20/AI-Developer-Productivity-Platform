package com.aiops.ai_analysis_service.dto;

import java.time.LocalDateTime;

public class AIAnalysisEvent {

    private Long incidentId;

    private String analysis;

    private String rootCause;

    private String recommendation;

    private Double confidenceScore;

    private LocalDateTime analyzedAt;

    public Long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(
            Long incidentId) {

        this.incidentId = incidentId;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(
            String analysis) {

        this.analysis = analysis;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(
            String rootCause) {

        this.rootCause = rootCause;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(
            String recommendation) {

        this.recommendation =
                recommendation;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(
            Double confidenceScore) {

        this.confidenceScore =
                confidenceScore;
    }

    public LocalDateTime getAnalyzedAt() {
        return analyzedAt;
    }

    public void setAnalyzedAt(
            LocalDateTime analyzedAt) {

        this.analyzedAt =
                analyzedAt;
    }
}