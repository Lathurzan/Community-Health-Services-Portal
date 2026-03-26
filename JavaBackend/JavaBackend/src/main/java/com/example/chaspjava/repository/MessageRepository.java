package com.example.chaspjava.repository;

import com.example.chaspjava.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {

    List<Message> findByRecipientId(String recipientId);

    List<Message> findBySenderId(String senderId);
}
