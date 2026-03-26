package com.example.chaspjava.controller;

import com.example.chaspjava.dto.SubscriptionDTO;
import com.example.chaspjava.entity.Subscription;
import com.example.chaspjava.service.SubscriptionService;
import com.example.chaspjava.service.NotificationService;
import com.example.chaspjava.service.MessageService;
import com.example.chaspjava.dto.NotificationDTO;
import com.example.chaspjava.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService service;
    private final NotificationService notificationService;
    private final MessageService messageService;

    @Autowired
    public SubscriptionController(SubscriptionService service, @Nullable NotificationService notificationService, @Nullable MessageService messageService) {
        this.service = service;
        this.notificationService = notificationService;
        this.messageService = messageService;
    }

    @PostMapping("/save")
    public Subscription save(@RequestBody SubscriptionDTO dto) {
        Subscription saved = service.createOrUpdate(dto);

        // send an in-app notification about successful subscription save if service is available
        try {
            if (notificationService != null) {
                NotificationDTO nd = new NotificationDTO();
                nd.setUserId(saved.getPatientId());
                nd.setNotificationType(com.example.chaspjava.entity.Notification.NotificationType.new_doctor);
                nd.setTitle("Subscription updated");
                nd.setMessage("Your subscription settings were saved successfully.");
                nd.setRelatedId(saved.getId());
                notificationService.create(nd);
            }

            if (messageService != null) {
                MessageDTO md = new MessageDTO();
                md.setSenderId("system");
                md.setRecipientId(saved.getPatientId());
                md.setSubject("Subscription saved");
                md.setContent("Your subscription preferences have been updated.");
                messageService.sendMessage(md);
            }
        } catch (Exception ex) {
            // swallow exceptions to keep save operation error-free for callers
        }

        return saved;
    }

    @GetMapping("/patient/{patientId}")
    public List<Subscription> getByPatient(@PathVariable String patientId) {
        return service.getByPatient(patientId);
    }

    @GetMapping
    public List<Subscription> getAll() {
        return service.getAll();
    }

    @GetMapping("/count")
    public long count() {
        return service.count();
    }
}
