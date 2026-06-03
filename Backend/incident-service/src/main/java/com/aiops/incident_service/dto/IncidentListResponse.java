package com.aiops.incident_service.dto;

import java.util.List;

public class IncidentListResponse {

    private List<IncidentResponse> incidents;

    private int currentPage;

    private int totalPages;

    private long totalElements;

    public List<IncidentResponse> getIncidents() {
        return incidents;
    }

    public void setIncidents(
            List<IncidentResponse> incidents) {

        this.incidents = incidents;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(
            int currentPage) {

        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(
            int totalPages) {

        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(
            long totalElements) {

        this.totalElements = totalElements;
    }
}