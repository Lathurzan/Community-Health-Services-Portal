package com.example.chaspjava.service;

import com.example.chaspjava.dto.MessageDTO;
import com.example.chaspjava.entity.Message;

import java.util.List;

public interface MessageService {

    Message sendMessage(MessageDTO dto);

    Message markAsRead(String messageId);

    List<Message> getMessagesForUser(String userId);

    List<Message> getSentMessages(String userId);
}
