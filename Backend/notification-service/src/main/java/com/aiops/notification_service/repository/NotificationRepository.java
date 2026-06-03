package com.aiops.notification_service.repository;

import com.aiops.notification_service.entity.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByIncidentId(
            Long incidentId);

    List<Notification> findByStatus(
            String status);
}