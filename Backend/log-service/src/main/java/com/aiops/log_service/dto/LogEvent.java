package com.aiops.log_service.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogEvent {

    private Long logId;
    private String serviceName;
    private String environment;
    private String severity;
    private String message;
    private LocalDateTime timestamp;
}
