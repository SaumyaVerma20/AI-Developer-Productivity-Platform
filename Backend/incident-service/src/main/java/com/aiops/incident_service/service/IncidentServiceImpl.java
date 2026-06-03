package com.aiops.incident_service.service;

import com.aiops.incident_service.dto.DashboardSummaryResponse;
import com.aiops.incident_service.dto.IncidentEvent;
import com.aiops.incident_service.dto.IncidentListResponse;
import com.aiops.incident_service.dto.IncidentResponse;
import com.aiops.incident_service.dto.TrendResponse;
import com.aiops.incident_service.entity.Incident;
import com.aiops.incident_service.entity.IncidentStatus;
import com.aiops.incident_service.exception.IncidentNotFoundException;
import com.aiops.incident_service.kafka.IncidentProducer;
import com.aiops.incident_service.repository.IncidentRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.aiops.incident_service.entity.Severity;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class IncidentServiceImpl
        implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final IncidentProducer incidentProducer;

    public IncidentServiceImpl(
            IncidentRepository incidentRepository, IncidentProducer incidentProducer) {

        this.incidentRepository = incidentRepository;
        this.incidentProducer = incidentProducer;
    }

    @Override
public IncidentResponse getIncidentById(
        Long incidentId) {

    Incident incident =
            incidentRepository
            .findById(incidentId)
            .orElseThrow(() ->
                    new IncidentNotFoundException(
                            "Incident not found with id: "
                                    + incidentId
                    ));

    return mapToResponse(
            incident);
}
   
@Override
public IncidentListResponse getAllIncidents(
        int page,
        int size,
        String status,
        String severity) {

    Pageable pageable =
            PageRequest.of(page, size);

    Page<Incident> incidents;

    if (status != null && severity != null) {

        incidents =
                incidentRepository
                .findByStatusAndSeverity(
                        IncidentStatus.valueOf(status),
                        Severity.valueOf(severity),
                        pageable
                );

    } else if (status != null) {

        incidents =
                incidentRepository
                .findByStatus(
                        IncidentStatus.valueOf(status),
                        pageable
                );

    } else if (severity != null) {

        incidents =
                incidentRepository
                .findBySeverity(
                        Severity.valueOf(severity),
                        pageable
                );

    } else {

        incidents =
                incidentRepository
                .findAll(pageable);
    }

    List<IncidentResponse> responseList =
            incidents.getContent()
                    .stream()
                    .map(this::mapToResponse)
                    .toList();

    IncidentListResponse response =
            new IncidentListResponse();

    response.setIncidents(responseList);
    response.setCurrentPage(incidents.getNumber());
    response.setTotalPages(incidents.getTotalPages());
    response.setTotalElements(incidents.getTotalElements());

    return response;
}


    @Override
public List<String> getIncidentTimeline(
        Long incidentId) {

    Incident incident =
            incidentRepository
            .findById(incidentId)
            .orElseThrow(() ->
                    new IncidentNotFoundException(
                            "Incident not found with id: "
                                    + incidentId
                    ));

    return List.of(
            "Incident created: "
                    + incident.getCreatedAt(),

            "Current status: "
                    + incident.getStatus(),

            "Severity: "
                    + incident.getSeverity()
    );
}

   @Override
public IncidentResponse resolveIncident(
        Long incidentId) {

    Incident incident =
            incidentRepository
            .findById(incidentId)
            .orElseThrow(() ->
                    new IncidentNotFoundException(
                            "Incident not found with id: "
                                    + incidentId
                    ));

    incident.setStatus(
        IncidentStatus.RESOLVED);

    incident.setResolvedAt(
            LocalDateTime.now());

    Incident updatedIncident =
            incidentRepository
                    .save(incident);

    return mapToResponse(
            updatedIncident);
}

    @Override
public DashboardSummaryResponse
getDashboardSummary() {

    DashboardSummaryResponse response =
            new DashboardSummaryResponse();

    response.setTotalIncidents(
            incidentRepository.count());

    response.setOpenIncidents(
            incidentRepository.countByStatus(
                    IncidentStatus.OPEN));

    response.setResolvedIncidents(
            incidentRepository.countByStatus(
                    IncidentStatus.RESOLVED));

    return response;
}

    @Override
public List<TrendResponse> getTrends() {

    TrendResponse day1 =
            new TrendResponse();

    day1.setDate("2026-05-28");
    day1.setIncidentCount(12);

    TrendResponse day2 =
            new TrendResponse();

    day2.setDate("2026-05-29");
    day2.setIncidentCount(18);

    TrendResponse day3 =
            new TrendResponse();

    day3.setDate("2026-05-30");
    day3.setIncidentCount(9);

    return List.of(
            day1,
            day2,
            day3
    );
}


    private IncidentResponse mapToResponse(
        Incident incident) {

    IncidentResponse response =
            new IncidentResponse();

    response.setId(
            incident.getId());

    response.setTitle(
            incident.getTitle());

    response.setDescription(
            incident.getDescription());

    response.setStatus(
            incident.getStatus());

    response.setSeverity(
            incident.getSeverity());

    response.setCreatedAt(
            incident.getCreatedAt());

    response.setResolvedAt(
            incident.getResolvedAt());

    return response;
}

@Override
public IncidentResponse createIncident(Incident incident) {

    incident.setCreatedAt(LocalDateTime.now());
    incident.setStatus(IncidentStatus.OPEN);

    Incident savedIncident =
            incidentRepository.save(incident);

    IncidentEvent event = new IncidentEvent(
            savedIncident.getId(),
            savedIncident.getTitle(),
            savedIncident.getDescription(),
            savedIncident.getSeverity(),
            savedIncident.getCreatedAt()
    );

    incidentProducer.publishIncident(event);

    return mapToResponse(savedIncident);
}
}