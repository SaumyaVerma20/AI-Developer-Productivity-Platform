package com.aiops.incident_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrendResponse {

    private String date;

    private long incidentCount;

    public TrendResponse() {
    }

    //generate getters/setters
}