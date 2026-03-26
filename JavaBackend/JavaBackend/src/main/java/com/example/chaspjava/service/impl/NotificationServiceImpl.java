package com.example.chaspjava.service.impl;

import com.example.chaspjava.dto.NotificationDTO;
import com.example.chaspjava.entity.Notification;
import com.example.chaspjava.repository.NotificationRepository;
import com.example.chaspjava.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repo;

    public NotificationServiceImpl(NotificationRepository repo) {
        this.repo = repo;
    }

    @Override
    public Notification create(NotificationDTO dto) {
        Notification n = new Notification();

        n.setId(UUID.randomUUID().toString());
        n.setUserId(dto.getUserId());
        n.setNotificationType(dto.getNotificationType());
        n.setTitle(dto.getTitle());
        n.setMessage(dto.getMessage());
        n.setRelatedId(dto.getRelatedId());

        return repo.save(n);
    }

    @Override
    public Notification markAsRead(String id) {
        Notification n = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        n.setReadStatus(true);
        return repo.save(n);
    }

    @Override
    public List<Notification> getUserNotifications(String userId) {
        return repo.findByUserId(userId);
    }

    @Override
    public List<Notification> getUnreadNotifications(String userId) {
        return repo.findByUserIdAndReadStatus(userId, false);
    }

    @Override
    public List<Notification> getUserNotificationsBySenderRole(String userId, Notification.SenderRole senderRole) {
        return repo.findByUserIdAndSenderRole(userId, senderRole);
    }

    @Override
    public List<Notification> getUserNotificationsBySenderRoles(String userId, java.util.Collection<Notification.SenderRole> senderRoles) {
        return repo.findByUserIdAndSenderRoleIn(userId, senderRoles);
    }

    @Override
    public Notification getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }
}
