package com.aiops.ai_analysis_service.dto;

public class AIAnalysisResponse {

    private Long incidentId;

    private String analysis;

    private String rootCause;

    private String recommendation;

    private String severityAssessment;

    private Double confidenceScore;

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

        this.recommendation = recommendation;
    }

    public String getSeverityAssessment() {
        return severityAssessment;
    }

    public void setSeverityAssessment(
            String severityAssessment) {

        this.severityAssessment =
                severityAssessment;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(
            Double confidenceScore) {

        this.confidenceScore =
                confidenceScore;
    }
}