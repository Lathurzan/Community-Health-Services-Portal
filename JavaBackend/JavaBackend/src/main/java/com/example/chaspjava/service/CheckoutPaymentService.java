package com.example.chaspjava.service;

import com.example.chaspjava.dto.CheckoutResponse;
import com.example.chaspjava.dto.PaymentDTO;
import com.example.chaspjava.entity.Payment;
import com.example.chaspjava.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CheckoutPaymentService {

    private final PaymentRepository paymentRepository;

    @Value("${checkout.success-url:}")
    private String successUrl;

    @Value("${checkout.cancel-url:}")
    private String cancelUrl;

    public CheckoutPaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public CheckoutResponse createSession(PaymentDTO dto) {
        if (dto == null) throw new IllegalArgumentException("payment dto is required");
    // userId is optional for donations; allow anonymous donations
        if (dto.getAmount() == null) throw new IllegalArgumentException("amount is required");

        String sessionId = UUID.randomUUID().toString();
        String url = (successUrl != null && !successUrl.isEmpty()) ? successUrl + "?session_id=" + sessionId : "https://example.com/checkout/complete?session_id=" + sessionId;

        Payment payment = new Payment();
    payment.setId(UUID.randomUUID().toString());
    payment.setUserId(dto.getUserId() != null && !dto.getUserId().isBlank() ? dto.getUserId() : payment.getId());
        payment.setPaymentMethod(Payment.PaymentMethod.checkout_com);
        payment.setAmount(dto.getAmount());
        payment.setCurrency(dto.getCurrency() != null ? dto.getCurrency() : "GBP");
        payment.setPurpose(dto.getPurpose() != null ? dto.getPurpose() : Payment.Purpose.service_donation);
        payment.setStatus(Payment.Status.pending);
        payment.setTransactionId(sessionId);
        payment.setPaymentDetails("{\"checkout_session_id\":\"" + sessionId + "\"}");

        paymentRepository.save(payment);

        return new CheckoutResponse(sessionId, url);
    }
}
