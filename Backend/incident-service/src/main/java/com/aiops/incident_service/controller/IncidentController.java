package com.aiops.incident_service.controller;

import com.aiops.incident_service.dto.DashboardSummaryResponse;
import com.aiops.incident_service.dto.IncidentListResponse;
import com.aiops.incident_service.dto.IncidentResponse;
import com.aiops.incident_service.dto.TrendResponse;
import com.aiops.incident_service.entity.Incident;
import com.aiops.incident_service.service.IncidentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(
            IncidentService incidentService) {

        this.incidentService = incidentService;
    }

    @GetMapping("/incidents/{incidentId}")
    public IncidentResponse getIncident(
            @PathVariable Long incidentId) {

        return incidentService
                .getIncidentById(incidentId);
    }

    @GetMapping("/incidents")
    public IncidentListResponse getIncidents(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(required = false)
            String status,

            @RequestParam(required = false)
            String severity
    ) {

        return incidentService.getAllIncidents(
                page,
                size,
                status,
                severity
        );
    }

    @GetMapping(
            "/incidents/{incidentId}/timeline")
    public List<String> getTimeline(
            @PathVariable Long incidentId) {

        return incidentService
                .getIncidentTimeline(incidentId);
    }

    @PatchMapping(
            "/incidents/{incidentId}/resolve")
    public IncidentResponse resolveIncident(
            @PathVariable Long incidentId) {

        return incidentService
                .resolveIncident(incidentId);
    }

    @GetMapping("/dashboard/summary")
    public DashboardSummaryResponse
    getSummary() {

        return incidentService
                .getDashboardSummary();
    }

    @GetMapping("/dashboard/trends")
    public List<TrendResponse>
    getTrends() {

        return incidentService.getTrends();
    }

    @PostMapping("/incidents")
public IncidentResponse createIncident(
        @RequestBody Incident incident) {

    return incidentService
            .createIncident(
                    incident);
}
}