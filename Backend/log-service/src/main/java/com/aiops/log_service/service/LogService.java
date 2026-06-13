package com.aiops.log_service.service;

import com.aiops.log_service.dto.*;
import com.aiops.log_service.entity.*;
import com.aiops.log_service.kafka.LogProducer;
import com.aiops.log_service.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class LogService {

    private final LogRepository logRepository;
    private final LogProducer logProducer;

    public LogService(LogRepository logRepository, LogProducer logProducer) {
        this.logRepository = logRepository;
        this.logProducer = logProducer;
    }

    public LogUploadResponse uploadLogs(LogUploadRequest request) {
        Severity severity = parseSeverity(request.getSeverity());
        LogEntry logEntry = saveLog(
                request.getServiceName(),
                request.getEnvironment() != null ? request.getEnvironment() : "DEV",
                severity,
                SourceType.MANUAL,
                request.getLogs(),
                LogStatus.PROCESSING
        );

        publishEvent(logEntry);

        return LogUploadResponse.builder()
                .incidentId(logEntry.getId())
                .status(logEntry.getStatus().name())
                .build();
    }

    public LogUploadResponse uploadLogFile(MultipartFile file, String serviceName, String environment, String severityStr) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".log") && !fileName.endsWith(".txt") && !fileName.endsWith(".json"))) {
            throw new IllegalArgumentException("Unsupported file extension. Allowed: .log, .txt, .json");
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("File size exceeds maximum limit of 10MB");
        }

        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            Severity severity = parseSeverity(severityStr != null ? severityStr : "MEDIUM");
            LogEntry logEntry = saveLog(
                    serviceName != null ? serviceName : "uploaded-service",
                    environment != null ? environment : "DEV",
                    severity,
                    SourceType.FILE_UPLOAD,
                    content,
                    LogStatus.PROCESSING
            );

            publishEvent(logEntry);

            return LogUploadResponse.builder()
                    .incidentId(logEntry.getId())
                    .status(logEntry.getStatus().name())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read uploaded file", e);
        }
    }

    public LogUploadResponse ingestLogs(LogIngestRequest request) {
        Severity severity = parseSeverity(request.getSeverity());
        LogEntry logEntry = saveLog(
                request.getServiceName(),
                "SYSTEM",
                severity,
                SourceType.SYSTEM,
                request.getMessage(),
                LogStatus.PROCESSING
        );

        publishEvent(logEntry);

        return LogUploadResponse.builder()
                .incidentId(logEntry.getId())
                .status(logEntry.getStatus().name())
                .build();
    }

    public void publishEvent(LogEntry logEntry) {
        LogEvent event = LogEvent.builder()
                .logId(logEntry.getId())
                .serviceName(logEntry.getServiceName())
                .environment(logEntry.getEnvironment())
                .severity(logEntry.getSeverity().name())
                .message(logEntry.getMessage())
                .timestamp(logEntry.getCreatedAt())
                .build();

        try {
            logProducer.publishLog(event);
            logEntry.setStatus(LogStatus.COMPLETED);
            logRepository.save(logEntry);
        } catch (Exception e) {
            logEntry.setStatus(LogStatus.FAILED);
            logRepository.save(logEntry);
            throw new RuntimeException("Failed to publish log event to Kafka", e);
        }
    }

    public LogEntry saveLog(String serviceName, String environment, Severity severity, SourceType sourceType, String message, LogStatus status) {
        LogEntry entry = LogEntry.builder()
                .serviceName(serviceName)
                .environment(environment)
                .severity(severity)
                .sourceType(sourceType)
                .message(message)
                .status(status)
                .createdAt(LocalDateTime.now())
                .build();

        return logRepository.save(entry);
    }

    public LogEntry getLogById(Long id) {
        return logRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Log entry not found with id: " + id));
    }

    private Severity parseSeverity(String severityStr) {
        try {
            return Severity.valueOf(severityStr.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid severity value: " + severityStr);
        }
    }
}
