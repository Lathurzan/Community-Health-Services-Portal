package com.example.chaspjava.controller;

import com.example.chaspjava.dto.MessageDTO;
import com.example.chaspjava.entity.Message;
import com.example.chaspjava.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public Message send(@RequestBody MessageDTO dto) {
        return service.sendMessage(dto);
    }

    @PostMapping("/read/{id}")
    public Message markAsRead(@PathVariable String id) {
        return service.markAsRead(id);
    }

    @GetMapping("/inbox/{userId}")
    public List<Message> inbox(@PathVariable String userId) {
        return service.getMessagesForUser(userId);
    }

    @GetMapping("/sent/{userId}")
    public List<Message> sent(@PathVariable String userId) {
        return service.getSentMessages(userId);
    }
}
