package com.example.chaspjava.service;

import com.example.chaspjava.dto.PayPalOrderResponse;
import com.example.chaspjava.dto.PaymentDTO;
import com.example.chaspjava.entity.Payment;
import com.example.chaspjava.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

/**
 * Lightweight stub for PayPal integration to avoid a hard dependency on the
 * PayPal Java SDK during compilation in this exercise. It creates a fake
 * order id and approval URL, persists a pending Payment and returns the
 * data expected by the controller.
 *
 * If you need real PayPal integration later, replace this implementation
 * with the official SDK-based code and add the correct dependency.
 */
@Service
public class PaypalPaymentService {

    private final PaymentRepository paymentRepository;

    @Value("${paypal.success-url:}")
    private String successUrl;

    @Value("${paypal.cancel-url:}")
    private String cancelUrl;

    public PaypalPaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PayPalOrderResponse createOrder(PaymentDTO dto) throws IOException {
    if (dto == null) throw new IllegalArgumentException("payment dto is required");
    // userId is optional for donations; allow anonymous donations
    if (dto.getAmount() == null) throw new IllegalArgumentException("amount is required");

    // Generate a fake PayPal order id and approval URL so the app can build
        // the rest of the flow without the external SDK present.
        String orderId = UUID.randomUUID().toString();
        String approvalUrl = (successUrl != null && !successUrl.isEmpty())
                ? successUrl + "?token=" + orderId
                : "https://example.com/paypal/approve?token=" + orderId;

        // Persist pending payment
        Payment payment = new Payment();
    payment.setId(UUID.randomUUID().toString());
    payment.setUserId(dto.getUserId() != null && !dto.getUserId().isBlank() ? dto.getUserId() : payment.getId());
        payment.setPaymentMethod(Payment.PaymentMethod.paypal);
        payment.setAmount(dto.getAmount());
        payment.setCurrency(dto.getCurrency() != null ? dto.getCurrency() : "GBP");
        payment.setPurpose(dto.getPurpose() != null ? dto.getPurpose() : Payment.Purpose.service_donation);
        payment.setStatus(Payment.Status.pending);
        payment.setTransactionId(orderId);
        payment.setPaymentDetails("{\"paypal_order_id\":\"" + orderId + "\"}");

        paymentRepository.save(payment);

        return new PayPalOrderResponse(orderId, approvalUrl);
    }
}
