package com.aiops.incident_service.service;

import com.aiops.incident_service.dto.DashboardSummaryResponse;
import com.aiops.incident_service.dto.IncidentListResponse;
import com.aiops.incident_service.dto.IncidentResponse;
import com.aiops.incident_service.dto.TrendResponse;
import com.aiops.incident_service.repository.IncidentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentServiceImpl
        implements IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentServiceImpl(
            IncidentRepository incidentRepository) {

        this.incidentRepository =
                incidentRepository;
    }

    @Override
    public IncidentResponse getIncidentById(
            Long incidentId) {

        return null;
    }

    @Override
    public IncidentListResponse getAllIncidents(
            int page,
            int size,
            String status,
            String severity) {

        return null;
    }

    @Override
    public List<String> getIncidentTimeline(
            Long incidentId) {

        return null;
    }

    @Override
    public IncidentResponse resolveIncident(
            Long incidentId) {

        return null;
    }

    @Override
    public DashboardSummaryResponse
    getDashboardSummary() {

        return null;
    }

    @Override
    public List<TrendResponse> getTrends() {

        return null;
    }

}