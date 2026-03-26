package com.example.chaspjava.controller;

import com.example.chaspjava.dto.PayPalOrderResponse;
import com.example.chaspjava.dto.PaymentDTO;
import com.example.chaspjava.dto.StripeCheckoutResponse;
import com.example.chaspjava.entity.Payment;
import com.example.chaspjava.service.PaymentService;
import com.example.chaspjava.service.PaypalPaymentService;
import com.example.chaspjava.service.StripePaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;
    private final StripePaymentService stripePaymentService;
    private final PaypalPaymentService paypalPaymentService;

    public PaymentController(
            PaymentService service,
            StripePaymentService stripePaymentService,
            PaypalPaymentService paypalPaymentService
    ) {
        this.service = service;
        this.stripePaymentService = stripePaymentService;
        this.paypalPaymentService = paypalPaymentService;
    }


    @PostMapping("/create")
    public Payment create(@RequestBody PaymentDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}/status/{status}")
    public Payment updateStatus(@PathVariable String id, @PathVariable Payment.Status status) {
        return service.updateStatus(id, status);
    }

    @GetMapping("/user/{userId}")
    public List<Payment> getByUser(@PathVariable String userId) {
        return service.getByUser(userId);
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable String id) {
        return service.getById(id);
    }

    // ==================== STRIPE ====================

    @PostMapping("/stripe/checkout")
    public StripeCheckoutResponse createStripeCheckout(@RequestBody PaymentDTO dto) {
        try {
            return stripePaymentService.createCheckoutSession(dto);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Stripe error: " + e.getMessage(),
                    e
            );
        }
    }

    // ==================== PAYPAL (Stub Only) ====================

    @PostMapping("/paypal/order")
    public PayPalOrderResponse createPaypalOrder(@RequestBody PaymentDTO dto) {
        try {
            return paypalPaymentService.createOrder(dto);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "PayPal error: " + e.getMessage(),
                    e
            );
        }
    }
}
