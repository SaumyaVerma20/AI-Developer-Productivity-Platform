package com.aiops.incident_service.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class DashboardSummaryResponse {

    private long totalIncidents;

    private long openIncidents;

    private long inProgressIncidents;

    private long resolvedIncidents;

    public DashboardSummaryResponse() {
    }

    //generate getters/setters
}