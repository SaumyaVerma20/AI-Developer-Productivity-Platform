package com.aiops.ai_analysis_service.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_analysis")
public class AIAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long incidentId;

    @Column(columnDefinition = "TEXT")
    private String incidentTitle;

    @Column(columnDefinition = "TEXT")
    private String incidentDescription;

    @Column(columnDefinition = "TEXT")
    private String rootCause;

    @Column(columnDefinition = "TEXT")
    private String recommendation;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String severity;

    private LocalDateTime analyzedAt;

    public AIAnalysis() {
    }

    public Long getId() {
        return id;
    }

    public void setId(
            Long id) {

        this.id = id;
    }

    public Long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(
            Long incidentId) {

        this.incidentId = incidentId;
    }

    public String getIncidentTitle() {
        return incidentTitle;
    }

    public void setIncidentTitle(
            String incidentTitle) {

        this.incidentTitle = incidentTitle;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(
            String incidentDescription) {

        this.incidentDescription =
                incidentDescription;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(
            String summary) {

        this.summary = summary;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(
            String severity) {

        this.severity = severity;
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