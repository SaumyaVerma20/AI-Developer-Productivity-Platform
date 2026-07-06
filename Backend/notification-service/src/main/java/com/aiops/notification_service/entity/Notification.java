package com.aiops.notification_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long incidentId;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String severity;

    private String recipient;

    private String status;

    private LocalDateTime sentAt;
}