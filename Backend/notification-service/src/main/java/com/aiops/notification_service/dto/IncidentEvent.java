package com.aiops.notification_service.dto;

import lombok.*;
import lombok.Setter;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentEvent {

    private Long incidentId;
    private String title;
    private String description;
    private String severity;
    private LocalDateTime createdAt;
}