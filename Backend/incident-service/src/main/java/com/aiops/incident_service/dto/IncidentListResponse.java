package com.aiops.incident_service.dto;

import java.util.List;

public class IncidentListResponse {

    private List<IncidentResponse> incidents;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    public IncidentListResponse() {
    }

    public List<IncidentResponse> getIncidents() {
        return incidents;
    }

    public void setIncidents(
            List<IncidentResponse> incidents) {
        this.incidents = incidents;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(
            long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(
            int totalPages) {
        this.totalPages = totalPages;
    }
}