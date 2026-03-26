package com.example.chaspjava.controller;

import com.example.chaspjava.dto.MessageDTO;
import com.example.chaspjava.entity.Message;
import com.example.chaspjava.service.MessageService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc(addFilters = false)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void sendMessage_returnsOk() throws Exception {
        MessageDTO dto = new MessageDTO();
        when(service.sendMessage(any(MessageDTO.class))).thenReturn(new Message());

        mockMvc.perform(post("/api/messages/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void markAsRead_returnsMessage() throws Exception {
        Message m = new Message();
        m.setId("msg-1");

        when(service.markAsRead("msg-1")).thenReturn(m);

        mockMvc.perform(post("/api/messages/read/msg-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("msg-1"));
    }

    @Test
    void inbox_returnsList() throws Exception {
        Message m = new Message();
        m.setId("msg-1");

        when(service.getMessagesForUser("user-1")).thenReturn(java.util.List.of(m));

        mockMvc.perform(get("/api/messages/inbox/user-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("msg-1"));
    }

    @Test
    void sent_returnsList() throws Exception {
        Message m = new Message();
        m.setId("msg-1");

        when(service.getSentMessages("user-1")).thenReturn(java.util.List.of(m));

        mockMvc.perform(get("/api/messages/sent/user-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("msg-1"));
    }
}
