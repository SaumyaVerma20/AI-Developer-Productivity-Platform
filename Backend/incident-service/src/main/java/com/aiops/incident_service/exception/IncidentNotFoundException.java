package com.aiops.incident_service.exception;

public class IncidentNotFoundException
        extends RuntimeException {

    public IncidentNotFoundException(
            String message
    ) {
        super(message);
    }
}