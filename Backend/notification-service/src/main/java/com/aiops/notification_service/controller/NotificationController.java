package com.aiops.notification_service.controller;

import com.aiops.notification_service.dto.NotificationRequest;
import com.aiops.notification_service.dto.NotificationResponse;
import com.aiops.notification_service.service.NotificationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal")
public class NotificationController {

    private final NotificationService
            notificationService;

    public NotificationController(
            NotificationService
                    notificationService) {

        this.notificationService =
                notificationService;
    }

    @PostMapping("/notify")
    public NotificationResponse
    sendNotification(
            @RequestBody
            NotificationRequest request) {

        return notificationService
                .sendNotification(
                        request);
    }

    @GetMapping("/notifications/{incidentId}")
    public List<NotificationResponse>
    getNotifications(
            @PathVariable
            Long incidentId) {

        return notificationService
                .getNotificationsByIncident(
                        incidentId);
    }
}