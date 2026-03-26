package com.example.chaspjava.service;

public interface EmailService {
    void sendVerificationCode(String toEmail, String code, String fullName);
}
