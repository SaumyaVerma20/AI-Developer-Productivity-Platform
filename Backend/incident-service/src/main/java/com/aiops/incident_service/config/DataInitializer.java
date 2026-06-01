package com.aiops.incident_service.config;

import com.aiops.incident_service.entity.Incident;
import com.aiops.incident_service.entity.Severity;
import com.aiops.incident_service.entity.IncidentStatus;
import com.aiops.incident_service.repository.IncidentRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadData(
            IncidentRepository incidentRepository) {

        return args -> {

            if (incidentRepository.count() == 0) {

                Incident incident1 =
                        new Incident();

                incident1.setTitle(
                        "Database connection timeout");

                incident1.setDescription(
                        "Repeated database timeout detected");

                incident1.setSeverity(
                        Severity.HIGH);

                incident1.setStatus(
                        IncidentStatus.OPEN);

                incident1.setCreatedAt(
                        LocalDateTime.now());



                Incident incident2 =
                        new Incident();

                incident2.setTitle(
                        "Memory usage spike");

                incident2.setDescription(
                        "Memory exceeded threshold");

                incident2.setSeverity(
                        Severity.MEDIUM);

                incident2.setStatus(
                        IncidentStatus.RESOLVED);

                incident2.setCreatedAt(
                        LocalDateTime.now());

                incidentRepository.save(
                        incident1);

                incidentRepository.save(
                        incident2);
            }
        };
    }
}