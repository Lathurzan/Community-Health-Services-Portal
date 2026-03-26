package com.example.chaspjava.controller;

import com.example.chaspjava.dto.LoginRequest;
import com.example.chaspjava.dto.RegisterRequest;
import com.example.chaspjava.dto.AuthResponse;
import com.example.chaspjava.service.AuthService;
import com.example.chaspjava.service.EmailService;
import com.example.chaspjava.service.VerificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService service;

    @MockBean
    private EmailService emailService;

    @MockBean
    private VerificationService verificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_returnsAuthResponse() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setFullName("Test User");
        req.setEmail("test@example.com");
        req.setPassword("secret12");
        req.setRole("patient");

    AuthResponse resp = new AuthResponse("token-1", "user-1", "test@example.com", "patient");

        when(service.register(any(RegisterRequest.class))).thenReturn(resp);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-1"));
    }

    @Test
    void register_missingFullName_returnsBadRequest() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("test@example.com");
        req.setPassword("secret12");
        req.setRole("patient");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_shortPassword_returnsBadRequest() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setFullName("Test User");
        req.setEmail("test@example.com");
        req.setPassword("123");
        req.setRole("patient");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_invalidRole_returnsBadRequest() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setFullName("Test User");
        req.setEmail("test@example.com");
        req.setPassword("secret12");
        req.setRole("invalid");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_returnsAuthResponse() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@example.com");
        req.setPassword("secret12");

        AuthResponse resp = new AuthResponse("token-abc", "user-2", "test@example.com", "patient");
        when(service.login(any(LoginRequest.class))).thenReturn(resp);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-abc"));
    }

    @Test
    void sendVerification_success_returnsOk() throws Exception {
        com.example.chaspjava.dto.SendVerificationRequest req = new com.example.chaspjava.dto.SendVerificationRequest();
        req.setEmail("notify@example.com");
        req.setFullName("Notify User");

        when(verificationService.generateAndStore("notify@example.com")).thenReturn("code-123");
        doNothing().when(emailService).sendVerificationCode("notify@example.com", "code-123", "Notify User");

        mockMvc.perform(post("/api/auth/send-verification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void sendVerification_missingEmail_returnsBadRequest() throws Exception {
        com.example.chaspjava.dto.SendVerificationRequest req = new com.example.chaspjava.dto.SendVerificationRequest();
        req.setFullName("No Email");

        mockMvc.perform(post("/api/auth/send-verification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sendVerification_emailSendFails_returnsServerError() throws Exception {
        com.example.chaspjava.dto.SendVerificationRequest req = new com.example.chaspjava.dto.SendVerificationRequest();
        req.setEmail("notify@example.com");
        req.setFullName("Notify User");

        when(verificationService.generateAndStore("notify@example.com")).thenReturn("code-123");
        doThrow(new RuntimeException("smtp failed")).when(emailService).sendVerificationCode("notify@example.com", "code-123", "Notify User");

        mockMvc.perform(post("/api/auth/send-verification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isInternalServerError());
    }
}
