package com.example.chaspjava.service;

import com.example.chaspjava.dto.NotificationDTO;
import com.example.chaspjava.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification create(NotificationDTO dto);

    Notification markAsRead(String id);

    List<Notification> getUserNotifications(String userId);

    List<Notification> getUnreadNotifications(String userId);

    List<Notification> getUserNotificationsBySenderRole(String userId, Notification.SenderRole senderRole);

    List<Notification> getUserNotificationsBySenderRoles(String userId, java.util.Collection<Notification.SenderRole> senderRoles);

    Notification getById(String id);
}
