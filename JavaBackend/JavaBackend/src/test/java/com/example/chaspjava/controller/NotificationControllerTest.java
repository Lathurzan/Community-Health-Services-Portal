package com.example.chaspjava.controller;

import com.example.chaspjava.dto.NotificationDTO;
import com.example.chaspjava.entity.Notification;
import com.example.chaspjava.service.NotificationService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNotification_returnsOk() throws Exception {
        NotificationDTO dto = new NotificationDTO();
        when(service.create(any(NotificationDTO.class))).thenReturn(new Notification());

        mockMvc.perform(post("/api/notifications/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void markAsRead_returnsNotification() throws Exception {
        Notification n = new Notification();
        n.setId("not-1");

        when(service.markAsRead("not-1")).thenReturn(n);

        mockMvc.perform(put("/api/notifications/read/not-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("not-1"));
    }

    @Test
    void getAll_returnsList() throws Exception {
        Notification n = new Notification();
        n.setId("not-1");

        when(service.getUserNotifications("user-1")).thenReturn(java.util.List.of(n));

        mockMvc.perform(get("/api/notifications/user/user-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("not-1"));
    }

    @Test
    void getUnread_returnsList() throws Exception {
        Notification n = new Notification();
        n.setId("not-1");

        when(service.getUnreadNotifications("user-1")).thenReturn(java.util.List.of(n));

        mockMvc.perform(get("/api/notifications/user/user-1/unread"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("not-1"));
    }

    @Test
    void getBySenderRole_default_returnsList() throws Exception {
        Notification n = new Notification();
        n.setId("not-1");

        when(service.getUserNotificationsBySenderRoles(any(), any())).thenReturn(java.util.List.of(n));

        mockMvc.perform(get("/api/notifications/user/user-1/by-sender"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("not-1"));
    }

    @Test
    void getBySenderRole_specific_returnsList() throws Exception {
        Notification n = new Notification();
        n.setId("not-1");

        when(service.getUserNotificationsBySenderRole("user-1", Notification.SenderRole.admin)).thenReturn(java.util.List.of(n));

        mockMvc.perform(get("/api/notifications/user/user-1/by-sender").param("sender", "admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("not-1"));
    }

    @Test
    void getBySenderRole_invalidParam_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/notifications/user/user-1/by-sender").param("sender", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getById_returnsNotification() throws Exception {
        Notification n = new Notification();
        n.setId("not-1");

        when(service.getById("not-1")).thenReturn(n);

        mockMvc.perform(get("/api/notifications/not-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("not-1"));
    }
}
