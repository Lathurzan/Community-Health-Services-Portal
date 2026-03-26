package com.example.chaspjava.controller;

import com.example.chaspjava.dto.AuthResponse;
import com.example.chaspjava.dto.LoginRequest;
import com.example.chaspjava.dto.RegisterRequest;
import com.example.chaspjava.dto.SendVerificationRequest;
import com.example.chaspjava.service.AuthService;
import com.example.chaspjava.service.EmailService;
import com.example.chaspjava.service.VerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping({"/api/auth", "/api/users"})
public class AuthController {

    private final AuthService service;
    private final EmailService emailService;
    private final VerificationService verificationService;

    public AuthController(AuthService service, EmailService emailService, VerificationService verificationService) {
        this.service = service;
        this.emailService = emailService;
        this.verificationService = verificationService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        // Basic validation to avoid null/not-null persistence errors
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
        }

        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fullName is required");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email is required");
        }

        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password is required and must be at least 6 characters");
        }

        if (request.getRole() == null || request.getRole().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "role is required");
        }

        // ensure role is one of allowed values (matches Profile.Role enum values)
        String roleLower = request.getRole().trim().toLowerCase();
        if (!("patient".equals(roleLower) || "provider".equals(roleLower) || "admin".equals(roleLower))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "role must be one of: patient, provider, admin");
        }

        return service.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/send-verification")
    public void sendVerification(@RequestBody SendVerificationRequest request) {
        if (request == null || request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email is required");
        }

        String email = request.getEmail().trim();
        String code = verificationService.generateAndStore(email);

        try {
            emailService.sendVerificationCode(email, code, request.getFullName());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send verification email: " + e.getMessage());
        }
    }
}
