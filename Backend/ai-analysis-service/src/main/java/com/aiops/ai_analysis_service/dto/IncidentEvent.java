package com.aiops.ai_analysis_service.dto;

import java.time.LocalDateTime;

public class IncidentEvent {

    private Long incidentId;

    private String title;

    private String description;

    private String severity;

    private LocalDateTime createdAt;

    public Long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(
            Long incidentId) {

        this.incidentId = incidentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(
            String title) {

        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {

        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(
            String severity) {

        this.severity = severity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }
}