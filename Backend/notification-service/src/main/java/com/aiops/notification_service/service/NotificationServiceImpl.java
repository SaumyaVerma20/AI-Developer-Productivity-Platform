package com.aiops.notification_service.service;

import com.aiops.notification_service.dto.NotificationEvent;
import com.aiops.notification_service.dto.NotificationRequest;
import com.aiops.notification_service.dto.NotificationResponse;
import com.aiops.notification_service.entity.Notification;
import com.aiops.notification_service.repository.NotificationRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository
            notificationRepository;

    public NotificationServiceImpl(
            NotificationRepository
                    notificationRepository) {

        this.notificationRepository =
                notificationRepository;
    }

    @Override
    public NotificationResponse
    sendNotification(
            NotificationRequest request) {

        Notification notification =
                new Notification();

        notification.setIncidentId(
                request.getIncidentId());

        notification.setMessage(
                request.getMessage());

        notification.setSeverity(
                request.getSeverity());

        notification.setRecipient(
                request.getRecipient());

        notification.setStatus(
                "SENT");

        notification.setSentAt(
                LocalDateTime.now());

        Notification saved =
                notificationRepository
                        .save(notification);

        return mapToResponse(
                saved);
    }

    @Override
    public List<NotificationResponse>
    getNotificationsByIncident(
            Long incidentId) {

        return notificationRepository
                .findByIncidentId(
                        incidentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private NotificationResponse
    mapToResponse(
            Notification notification) {

        NotificationResponse response =
                new NotificationResponse();

        response.setId(
                notification.getId());

        response.setIncidentId(
                notification.getIncidentId());

        response.setMessage(
                notification.getMessage());

        response.setStatus(
                notification.getStatus());

        response.setSentAt(
                notification.getSentAt());

        return response;
    }


    @Override
public NotificationResponse
processNotification(
        NotificationEvent event) {

    Notification notification =
            new Notification();

    notification.setIncidentId(
            event.getIncidentId());

    notification.setMessage(
            event.getMessage());

    notification.setStatus(
            "SENT");

    notification.setSentAt(
            LocalDateTime.now());

    Notification saved =
            notificationRepository
                    .save(notification);

    return mapToResponse(saved);
}
}