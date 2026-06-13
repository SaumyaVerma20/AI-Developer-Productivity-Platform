package com.aiops.log_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogUploadRequest {

    @NotBlank(message = "Service name is required")
    @Size(max = 100, message = "Service name must not exceed 100 characters")
    private String serviceName;

    private String environment;

    @NotBlank(message = "Severity is required")
    private String severity;

    @NotBlank(message = "Logs content cannot be empty")
    private String logs;
}
