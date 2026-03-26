package com.example.chaspjava.controller;

import com.example.chaspjava.dto.PaymentDTO;
import com.example.chaspjava.dto.StripeCheckoutResponse;
import com.example.chaspjava.entity.Payment;
import com.example.chaspjava.service.PaymentService;
import com.example.chaspjava.service.StripePaymentService;
import com.example.chaspjava.service.PaypalPaymentService;
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
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService service;

    @MockBean
    private StripePaymentService stripePaymentService;

    @MockBean
    private PaypalPaymentService paypalPaymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createStripeCheckout_returnsResponse() throws Exception {
        PaymentDTO dto = new PaymentDTO();
        StripeCheckoutResponse resp = new StripeCheckoutResponse();
        resp.setSessionId("sess_1");

        when(stripePaymentService.createCheckoutSession(any(PaymentDTO.class))).thenReturn(resp);

        mockMvc.perform(post("/api/payments/stripe/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value("sess_1"));
    }

    @Test
    void createPaypalOrder_returnsResponse() throws Exception {
        PaymentDTO dto = new PaymentDTO();
        com.example.chaspjava.dto.PayPalOrderResponse resp = new com.example.chaspjava.dto.PayPalOrderResponse();
        resp.setOrderId("order-1");

        when(paypalPaymentService.createOrder(any(PaymentDTO.class))).thenReturn(resp);

        mockMvc.perform(post("/api/payments/paypal/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("order-1"));
    }

    @Test
    void createStripeCheckout_whenServiceThrows_returnsBadRequest() throws Exception {
        PaymentDTO dto = new PaymentDTO();

        when(stripePaymentService.createCheckoutSession(any(PaymentDTO.class))).thenThrow(new RuntimeException("stripe fail"));

        mockMvc.perform(post("/api/payments/stripe/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createPaypalOrder_whenServiceThrows_returnsBadRequest() throws Exception {
        PaymentDTO dto = new PaymentDTO();

        when(paypalPaymentService.createOrder(any(PaymentDTO.class))).thenThrow(new RuntimeException("paypal fail"));

        mockMvc.perform(post("/api/payments/paypal/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createPayment_returnsPayment() throws Exception {
        PaymentDTO dto = new PaymentDTO();
        Payment p = new Payment();
        p.setId("pay-1");

        when(service.create(any(PaymentDTO.class))).thenReturn(p);

        mockMvc.perform(post("/api/payments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("pay-1"));
    }

    @Test
    void updateStatus_returnsPayment() throws Exception {
        Payment p = new Payment();
        p.setId("pay-1");

        when(service.updateStatus("pay-1", Payment.Status.completed)).thenReturn(p);

        mockMvc.perform(put("/api/payments/pay-1/status/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("pay-1"));
    }

    @Test
    void getByUser_returnsList() throws Exception {
        Payment p = new Payment();
        p.setId("pay-1");

        when(service.getByUser("user-1")).thenReturn(java.util.List.of(p));

        mockMvc.perform(get("/api/payments/user/user-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("pay-1"));
    }

    @Test
    void getById_returnsPayment() throws Exception {
        Payment p = new Payment();
        p.setId("pay-1");

        when(service.getById("pay-1")).thenReturn(p);

        mockMvc.perform(get("/api/payments/pay-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("pay-1"));
    }
}
