package com.example.chaspjava.dto;

import com.example.chaspjava.entity.Notification;

public class NotificationDTO {

    private String userId;
    private String senderId;
    private Notification.SenderRole senderRole;
    private Notification.NotificationType notificationType;
    private String title;
    private String message;
    private String relatedId;

    // GETTERS & SETTERS
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public Notification.SenderRole getSenderRole() { return senderRole; }
    public void setSenderRole(Notification.SenderRole senderRole) { this.senderRole = senderRole; }

    public Notification.NotificationType getNotificationType() { return notificationType; }
    public void setNotificationType(Notification.NotificationType notificationType) { this.notificationType = notificationType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRelatedId() { return relatedId; }
    public void setRelatedId(String relatedId) { this.relatedId = relatedId; }
}
