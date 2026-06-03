package com.aiops.incident_service.service;

import com.aiops.incident_service.dto.DashboardSummaryResponse;
import com.aiops.incident_service.dto.IncidentListResponse;
import com.aiops.incident_service.dto.IncidentResponse;
import com.aiops.incident_service.dto.TrendResponse;
import com.aiops.incident_service.entity.Incident;

import java.util.List;

public interface IncidentService {

    IncidentResponse getIncidentById(
            Long incidentId);

    IncidentListResponse getAllIncidents(
            int page,
            int size,
            String status,
            String severity);

    List<String> getIncidentTimeline(
            Long incidentId);

    IncidentResponse resolveIncident(
            Long incidentId);

    DashboardSummaryResponse getDashboardSummary();

    List<TrendResponse> getTrends();

    IncidentResponse createIncident(
        Incident incident);

}