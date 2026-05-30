package com.aiops.incident_service.service;

import com.aiops.incident_service.dto.CreateIncidentRequest;
import com.aiops.incident_service.dto.IncidentResponse;
import com.aiops.incident_service.dto.UpdateIncidentRequest;
import com.aiops.incident_service.entity.Incident;
import com.aiops.incident_service.entity.IncidentStatus;
import com.aiops.incident_service.exception.IncidentNotFoundException;
import com.aiops.incident_service.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentResponse createIncident(
            CreateIncidentRequest request
    ) {

        Incident incident = Incident.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .severity(request.getSeverity())
                .status(IncidentStatus.OPEN)
                .createdBy("Saumya")
                .createdAt(LocalDateTime.now())
                .build();

        Incident savedIncident =
                incidentRepository.save(incident);

        return mapToResponse(savedIncident);
    }

    public List<IncidentResponse> getAllIncidents() {

        return incidentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public IncidentResponse getIncidentById(Long id) {

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() ->
                        new IncidentNotFoundException(
                                "Incident not found"
                        )
                );

        return mapToResponse(incident);
    }

    public IncidentResponse updateIncident(
            Long id,
            UpdateIncidentRequest request
    ) {

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() ->
                        new IncidentNotFoundException(
                                "Incident not found"
                        )
                );

        incident.setStatus(request.getStatus());

        Incident updatedIncident =
                incidentRepository.save(incident);

        return mapToResponse(updatedIncident);
    }

    public void deleteIncident(Long id) {

        incidentRepository.deleteById(id);
    }

    private IncidentResponse mapToResponse(
            Incident incident
    ) {

        return IncidentResponse.builder()
                .id(incident.getId())
                .title(incident.getTitle())
                .description(incident.getDescription())
                .severity(incident.getSeverity())
                .status(incident.getStatus())
                .createdBy(incident.getCreatedBy())
                .createdAt(incident.getCreatedAt())
                .build();
    }
}