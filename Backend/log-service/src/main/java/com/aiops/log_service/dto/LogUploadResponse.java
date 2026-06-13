package com.aiops.log_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogUploadResponse {

    private Long incidentId;
    private String status;
}
