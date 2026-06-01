package com.aiops.incident_service.repository;

import com.aiops.incident_service.entity.Incident;
import com.aiops.incident_service.entity.IncidentStatus;
import com.aiops.incident_service.entity.Severity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository
        extends JpaRepository<Incident, Long> {

    Page<Incident> findByStatus(
            IncidentStatus status,
            Pageable pageable
    );

    Page<Incident> findBySeverity(
            Severity severity,
            Pageable pageable
    );

    Page<Incident> findByStatusAndSeverity(
            IncidentStatus status,
            Severity severity,
            Pageable pageable
    );
}