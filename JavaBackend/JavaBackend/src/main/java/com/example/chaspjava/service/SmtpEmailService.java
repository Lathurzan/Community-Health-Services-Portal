package com.example.chaspjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class SmtpEmailService implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(SmtpEmailService.class);
    // basic email regex; good enough for simple validation
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Value("${spring.mail.username:}")
    private String fromAddress;

    // no external mail dependency required here; simulate sending by logging
    public SmtpEmailService() {
    }

    @Override
    public void sendVerificationCode(String toEmail, String code, String fullName) {
        if (toEmail == null || toEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipient email is required");
        }

        String to = toEmail.trim();
        if (!EMAIL_REGEX.matcher(to).matches()) {
            throw new IllegalArgumentException("Recipient email is not a valid email address");
        }
        String namePart = (fullName == null || fullName.trim().isEmpty()) ? "" : ("Hi " + fullName.trim() + ",\n\n");

        String subject = "Your verification code";
        String text = namePart + "Your verification code is: " + code + "\n\n" + "If you didn't request this, ignore this email.";

        // Simulate sending the email by logging the content; this removes the compile-time dependency
        if (fromAddress == null || fromAddress.trim().isEmpty()) {
            log.info("From address not configured; using system default for simulated send.");
        }
        log.info("Simulated send of verification email to {} - subject: {} - body: {}", to, subject, text);
    }
}
