package com.example.chaspjava.controller;

import com.example.chaspjava.dto.PaymentDTO;
import com.example.chaspjava.dto.StripeCheckoutResponse;
import com.example.chaspjava.dto.PayPalOrderResponse;
import com.example.chaspjava.dto.CheckoutResponse;
import com.example.chaspjava.service.PaypalPaymentService;
import com.example.chaspjava.service.CheckoutPaymentService;
import com.example.chaspjava.service.StripePaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/donate")
public class DonationController {

    private final StripePaymentService stripePaymentService;
    private final PaypalPaymentService paypalPaymentService;
    private final CheckoutPaymentService checkoutPaymentService;

    public DonationController(StripePaymentService stripePaymentService, PaypalPaymentService paypalPaymentService, CheckoutPaymentService checkoutPaymentService) {
        this.stripePaymentService = stripePaymentService;
        this.paypalPaymentService = paypalPaymentService;
        this.checkoutPaymentService = checkoutPaymentService;
    }

    @PostMapping("/stripe")
    public StripeCheckoutResponse createStripeCheckout(@RequestBody PaymentDTO dto) {
    // Let validation and Stripe errors propagate and be handled by global exception handler
    return stripePaymentService.createCheckoutSession(dto);
    }

    @PostMapping("/paypal")
    public PayPalOrderResponse createPaypalOrder(@RequestBody PaymentDTO dto) throws Exception {
        return paypalPaymentService.createOrder(dto);
    }

    @PostMapping("/checkout")
    public CheckoutResponse createCheckoutSession(@RequestBody PaymentDTO dto) {
        return checkoutPaymentService.createSession(dto);
    }
}
