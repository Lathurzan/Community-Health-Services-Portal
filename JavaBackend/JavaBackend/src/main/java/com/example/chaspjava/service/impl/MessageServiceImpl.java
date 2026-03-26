package com.example.chaspjava.service.impl;

import com.example.chaspjava.dto.MessageDTO;
import com.example.chaspjava.entity.Message;
import com.example.chaspjava.repository.MessageRepository;
import com.example.chaspjava.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repo;

    public MessageServiceImpl(MessageRepository repo) {
        this.repo = repo;
    }

    @Override
    public Message sendMessage(MessageDTO dto) {
        Message m = new Message();

        m.setId(UUID.randomUUID().toString());
        m.setSenderId(dto.getSenderId());
        m.setRecipientId(dto.getRecipientId());
        m.setSubject(dto.getSubject());
        m.setContent(dto.getContent());

        return repo.save(m);
    }

    @Override
    public Message markAsRead(String messageId) {
        Message msg = repo.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        msg.setReadStatus(true);
        return repo.save(msg);
    }

    @Override
    public List<Message> getMessagesForUser(String userId) {
        return repo.findByRecipientId(userId);
    }

    @Override
    public List<Message> getSentMessages(String userId) {
        return repo.findBySenderId(userId);
    }
}
