package com.example.chaspjava.controller;

import com.example.chaspjava.dto.SubscriptionDTO;
import com.example.chaspjava.entity.Subscription;
import com.example.chaspjava.service.SubscriptionService;
import com.example.chaspjava.service.NotificationService;
import com.example.chaspjava.service.MessageService;
import com.example.chaspjava.dto.NotificationDTO;
import com.example.chaspjava.dto.MessageDTO;
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
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubscriptionController.class)
@AutoConfigureMockMvc(addFilters = false)
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionService service;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private MessageService messageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveSubscription_returnsOk() throws Exception {
        SubscriptionDTO dto = new SubscriptionDTO();
        when(service.createOrUpdate(any(SubscriptionDTO.class))).thenReturn(new Subscription());

        mockMvc.perform(post("/api/subscriptions/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void saveSubscription_withNotificationAndMessage_callsServices() throws Exception {
        SubscriptionDTO dto = new SubscriptionDTO();
        Subscription saved = new Subscription();
        saved.setId("s-1");
        saved.setPatientId("p-1");

        when(service.createOrUpdate(any(SubscriptionDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/subscriptions/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(notificationService).create(any(NotificationDTO.class));
        verify(messageService).sendMessage(any(MessageDTO.class));
    }

    @Test
    void getByPatient_returnsList() throws Exception {
        Subscription s = new Subscription();
        s.setId("s-1");
        s.setPatientId("p-1");

        when(service.getByPatient("p-1")).thenReturn(java.util.List.of(s));

        mockMvc.perform(get("/api/subscriptions/patient/{patientId}", "p-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].patientId").value("p-1"));
    }

    @Test
    void getAll_returnsList() throws Exception {
        Subscription s = new Subscription();
        s.setId("s-1");
        when(service.getAll()).thenReturn(java.util.List.of(s));

        mockMvc.perform(get("/api/subscriptions")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    void count_returnsValue() throws Exception {
        when(service.count()).thenReturn(5L);

        mockMvc.perform(get("/api/subscriptions/count"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$").value(5));
    }
}
