package com.aiops.incident_service.dto;

import java.time.LocalDateTime;
import com.aiops.incident_service.entity.Severity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IncidentEvent {

    private Long incidentId;

    private String title;

    private String description;

    private Severity severity;

    private LocalDateTime createdAt;

    public IncidentEvent(
            Long incidentId,
            String title,
            String description,
            Severity severity) {

        this.incidentId = incidentId;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.createdAt = LocalDateTime.now();
    }

    

}