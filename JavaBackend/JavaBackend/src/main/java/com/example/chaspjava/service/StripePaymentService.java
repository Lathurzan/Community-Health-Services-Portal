package com.example.chaspjava.service;

import com.example.chaspjava.dto.PaymentDTO;
import com.example.chaspjava.dto.StripeCheckoutResponse;
import com.example.chaspjava.entity.Payment;
import com.example.chaspjava.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class StripePaymentService {

    private final PaymentRepository paymentRepository;
        private boolean useStub = false;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.success-url}")
    private String successUrl;

    @Value("${stripe.cancel-url}")
    private String cancelUrl;

    public StripePaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostConstruct
    public void init() {
                // If no valid Stripe key is provided (placeholder or blank), enable stub mode
                // so local/testing environments can exercise the API without valid credentials.
                if (stripeSecretKey == null || stripeSecretKey.isBlank()
                                || stripeSecretKey.toLowerCase().contains("your_")
                                || stripeSecretKey.toLowerCase().contains("xxxx")
                                || stripeSecretKey.toLowerCase().contains("placeholder")) {
                        useStub = true;
                } else {
                        Stripe.apiKey = stripeSecretKey;
                        useStub = false;
                }
    }


        public StripeCheckoutResponse createCheckoutSession(PaymentDTO dto) {
                                        // Basic validation: ensure required fields are present to avoid
                                        // persisting entities with null required properties (e.g. userId)
                                        if (dto == null) {
                                                throw new IllegalArgumentException("payment dto is required");
                                        }
                                        // userId is optional for donations; allow anonymous donations
                                        if (dto.getAmount() == null) {
                                                throw new IllegalArgumentException("amount is required");
                                        }

                                        // If we are running in stub mode (no valid API key), return a fake session
                if (useStub) {
                    String sessionId = UUID.randomUUID().toString();
                    String sessionUrl = (successUrl != null && !successUrl.isEmpty())
                            ? successUrl + "?session_id=" + sessionId
                            : "https://example.com/stripe/checkout?session_id=" + sessionId;

                    Payment payment = new Payment();
                    payment.setId(UUID.randomUUID().toString());
                    // If no userId supplied, use the generated payment id as a donation id
                    payment.setUserId(dto.getUserId() != null && !dto.getUserId().isBlank() ? dto.getUserId() : payment.getId());
                    payment.setPaymentMethod(Payment.PaymentMethod.stripe);
                    payment.setAmount(dto.getAmount());
                    payment.setCurrency(dto.getCurrency() != null ? dto.getCurrency() : "GBP");
                    payment.setPurpose(dto.getPurpose() != null ? dto.getPurpose() : Payment.Purpose.service_donation);
                    payment.setStatus(Payment.Status.pending);
                    payment.setTransactionId(sessionId);
                    payment.setPaymentDetails("{\"stripe_session_id\":\"" + sessionId + "\"}");

                    paymentRepository.save(payment);

                    return new StripeCheckoutResponse(sessionId, sessionUrl);
                }

                // Defensive defaults
                if (dto.getAmount() == null) {
                        throw new IllegalArgumentException("amount is required");
                }

                long amountInMinorUnits = dto.getAmount()
                                .multiply(BigDecimal.valueOf(100))
                                .longValueExact();

                String currency = dto.getCurrency() != null ? dto.getCurrency().toLowerCase() : "gbp";
                String productName = (dto.getPurpose() != null) ? dto.getPurpose().name() : "CHaSP Donation";

                SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                                .setMode(SessionCreateParams.Mode.PAYMENT)
                                .setSuccessUrl(successUrl)
                                .setCancelUrl(cancelUrl)
                                .addLineItem(
                                                SessionCreateParams.LineItem.builder()
                                                                .setQuantity(1L)
                                                                .setPriceData(
                                                                                SessionCreateParams.LineItem.PriceData.builder()
                                                                                                .setCurrency(currency)
                                                                                                .setUnitAmount(amountInMinorUnits)
                                                                                                .setProductData(
                                                                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                                                                                .setName(productName)
                                                                                                                                .build()
                                                                                                )
                                                                                                .build()
                                                                )
                                                                .build()
                                )
                                .putMetadata("purpose", productName);

                        if (dto.getUserId() != null && !dto.getUserId().isBlank()) {
                                paramsBuilder.putMetadata("userId", dto.getUserId());
                        }

                        SessionCreateParams params = paramsBuilder.build();

                        Session session;
                        try {
                                session = Session.create(params);
                        } catch (AuthenticationException ae) {
                                // Authentication failed (bad API key). Don't expose the key in logs or responses.
                                throw new IllegalStateException("Stripe authentication failed: invalid API key or misconfigured credentials");
                        } catch (StripeException se) {
                                // Wrap other Stripe errors with a generic message to surface to controller
                                throw new RuntimeException("Stripe API error: " + se.getMessage(), se);
                        }

                        // IMPORTANT: Do not persist the final successful payment here. The Checkout
                        // Session creates a PaymentIntent on Stripe when the customer completes
                        // the flow on the client. Persist a local Payment record only after you
                        // receive a successful webhook event (e.g. checkout.session.completed or
                        // payment_intent.succeeded). For local testing (useStub) we persisted a
                        // fake pending payment earlier. For real Stripe runs, return the session
                        // and let the webhook handler update/save the payment.

                        return new StripeCheckoutResponse(session.getId(), session.getUrl());
    }
}
