package com.aiops.notification_service.service;

import com.aiops.notification_service.dto.NotificationEvent;
import com.aiops.notification_service.dto.NotificationRequest;
import com.aiops.notification_service.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {

    NotificationResponse sendNotification(
            NotificationRequest request);

    List<NotificationResponse>
    getNotificationsByIncident(
            Long incidentId);

   NotificationResponse processNotification(
        NotificationEvent event);
}