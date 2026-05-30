package com.aiops.incident_service.controller;

import com.aiops.incident_service.dto.CreateIncidentRequest;
import com.aiops.incident_service.dto.IncidentResponse;
import com.aiops.incident_service.dto.UpdateIncidentRequest;
import com.aiops.incident_service.service.IncidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    @PostMapping
    public IncidentResponse createIncident(
            @Valid @RequestBody CreateIncidentRequest request
    ) {

        return incidentService.createIncident(request);
    }

    @GetMapping
    public List<IncidentResponse> getAllIncidents() {

        return incidentService.getAllIncidents();
    }

    @GetMapping("/{id}")
    public IncidentResponse getIncidentById(
            @PathVariable Long id
    ) {

        return incidentService.getIncidentById(id);
    }

    @PutMapping("/{id}")
    public IncidentResponse updateIncident(
            @PathVariable Long id,
            @RequestBody UpdateIncidentRequest request
    ) {

        return incidentService.updateIncident(
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    public void deleteIncident(
            @PathVariable Long id
    ) {

        incidentService.deleteIncident(id);
    }
}