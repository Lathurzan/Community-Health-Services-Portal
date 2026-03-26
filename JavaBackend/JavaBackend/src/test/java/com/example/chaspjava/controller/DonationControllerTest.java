package com.example.chaspjava.controller;

import com.example.chaspjava.dto.CheckoutResponse;
import com.example.chaspjava.dto.PayPalOrderResponse;
import com.example.chaspjava.dto.PaymentDTO;
import com.example.chaspjava.dto.StripeCheckoutResponse;
import com.example.chaspjava.service.CheckoutPaymentService;
import com.example.chaspjava.service.PaypalPaymentService;
import com.example.chaspjava.service.StripePaymentService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DonationController.class)
@AutoConfigureMockMvc(addFilters = false)
class DonationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StripePaymentService stripePaymentService;

    @MockBean
    private PaypalPaymentService paypalPaymentService;

    @MockBean
    private CheckoutPaymentService checkoutPaymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createStripeCheckout_returnsStripeResponse() throws Exception {
        PaymentDTO dto = new PaymentDTO();
        StripeCheckoutResponse resp = new StripeCheckoutResponse("sess-1", "https://stripe.example/checkout");

        when(stripePaymentService.createCheckoutSession(any(PaymentDTO.class))).thenReturn(resp);

        mockMvc.perform(post("/api/donate/stripe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value("sess-1"));
    }

    @Test
    void createPaypalOrder_returnsPaypalResponse() throws Exception {
        PaymentDTO dto = new PaymentDTO();
        PayPalOrderResponse resp = new PayPalOrderResponse("order-1", "https://paypal.example/approve");

        when(paypalPaymentService.createOrder(any(PaymentDTO.class))).thenReturn(resp);

        mockMvc.perform(post("/api/donate/paypal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("order-1"));
    }

    @Test
    void createCheckoutSession_returnsCheckoutResponse() throws Exception {
        PaymentDTO dto = new PaymentDTO();
        CheckoutResponse resp = new CheckoutResponse("sess-2", "https://checkout.example/session");

        when(checkoutPaymentService.createSession(any(PaymentDTO.class))).thenReturn(resp);

        mockMvc.perform(post("/api/donate/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value("sess-2"));
    }
}
