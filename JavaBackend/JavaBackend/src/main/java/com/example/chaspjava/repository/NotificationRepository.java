package com.example.chaspjava.repository;

import com.example.chaspjava.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findByUserId(String userId);

    List<Notification> findByUserIdAndReadStatus(String userId, Boolean readStatus);

    List<Notification> findByUserIdAndSenderRole(String userId, Notification.SenderRole senderRole);
    
        List<Notification> findByUserIdAndSenderRoleIn(String userId, java.util.Collection<Notification.SenderRole> senderRoles);

}
