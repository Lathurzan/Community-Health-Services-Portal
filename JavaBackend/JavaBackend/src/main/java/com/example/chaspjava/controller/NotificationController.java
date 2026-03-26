package com.example.chaspjava.controller;

import com.example.chaspjava.dto.NotificationDTO;
import com.example.chaspjava.entity.Notification;
import com.example.chaspjava.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Notification create(@RequestBody NotificationDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/read/{id}")
    public Notification markAsRead(@PathVariable String id) {
        return service.markAsRead(id);
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getAll(@PathVariable String userId) {
        return service.getUserNotifications(userId);
    }

    @GetMapping("/user/{userId}/unread")
    public List<Notification> getUnread(@PathVariable String userId) {
        return service.getUnreadNotifications(userId);
    }

    /**
     * Get notifications received by a user that were sent by an admin or provider.
     * Example: GET /api/notifications/user/{userId}/by-sender?sender=admin
     */
    @GetMapping("/user/{userId}/by-sender")
    public List<Notification> getBySenderRole(@PathVariable String userId,
                                              @RequestParam(name = "sender", required = false) String sender) {
        try {
            if (sender == null || sender.isBlank()) {
                // default to admin + provider
                java.util.List<Notification.SenderRole> defaults = java.util.List.of(Notification.SenderRole.admin, Notification.SenderRole.provider);
                return service.getUserNotificationsBySenderRoles(userId, defaults);
            }

            Notification.SenderRole role = Notification.SenderRole.valueOf(sender);
            return service.getUserNotificationsBySenderRole(userId, role);
        } catch (IllegalArgumentException ex) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "sender must be one of: admin, provider, patient", ex);
        }
    }

    @GetMapping("/{id}")
    public Notification getById(@PathVariable String id) {
        return service.getById(id);
    }
}
