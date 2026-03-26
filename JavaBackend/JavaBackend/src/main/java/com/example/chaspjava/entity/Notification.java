package com.example.chaspjava.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "user_id", length = 36, nullable = false)
    private String userId;

    @Column(name = "sender_id", length = 36)
    private String senderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "sender_role")
    private SenderRole senderRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "read_status")
    private Boolean readStatus = false;

    @Column(name = "sent_via", columnDefinition = "JSON")
    private String sentVia = "[\"in-app\"]";

    @Column(name = "related_id", length = 36)
    private String relatedId;

    private LocalDateTime createdAt;

    public enum NotificationType {
        medication_reminder,
        appointment_reminder,
        health_alert,
        new_doctor,
        new_message,
        test_results,
        prescription_ready
    }

    public enum SenderRole {
        patient, provider, admin
    }

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // ---- GETTERS & SETTERS ----

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public SenderRole getSenderRole() { return senderRole; }
    public void setSenderRole(SenderRole senderRole) { this.senderRole = senderRole; }

    public NotificationType getNotificationType() { return notificationType; }
    public void setNotificationType(NotificationType notificationType) { this.notificationType = notificationType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Boolean getReadStatus() { return readStatus; }
    public void setReadStatus(Boolean readStatus) { this.readStatus = readStatus; }

    public String getSentVia() { return sentVia; }
    public void setSentVia(String sentVia) { this.sentVia = sentVia; }

    public String getRelatedId() { return relatedId; }
    public void setRelatedId(String relatedId) { this.relatedId = relatedId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
