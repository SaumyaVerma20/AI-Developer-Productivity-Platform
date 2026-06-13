package com.aiops.log_service.controller;

import com.aiops.log_service.dto.LogIngestRequest;
import com.aiops.log_service.dto.LogUploadRequest;
import com.aiops.log_service.dto.LogUploadResponse;
import com.aiops.log_service.service.LogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping("/upload")
    public ResponseEntity<LogUploadResponse> uploadLogs(@Valid @RequestBody LogUploadRequest request) {
        LogUploadResponse response = logService.uploadLogs(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload/file")
    public ResponseEntity<LogUploadResponse> uploadLogFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "serviceName", required = false) String serviceName,
            @RequestParam(value = "environment", required = false) String environment,
            @RequestParam(value = "severity", required = false) String severity) {
        LogUploadResponse response = logService.uploadLogFile(file, serviceName, environment, severity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ingest")
    public ResponseEntity<LogUploadResponse> ingestLogs(@Valid @RequestBody LogIngestRequest request) {
        LogUploadResponse response = logService.ingestLogs(request);
        return ResponseEntity.ok(response);
    }
}
