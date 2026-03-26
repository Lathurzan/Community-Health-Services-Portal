package com.example.chaspjava.service.impl;

import com.example.chaspjava.dto.PaymentDTO;
import com.example.chaspjava.entity.Payment;
import com.example.chaspjava.repository.PaymentRepository;
import com.example.chaspjava.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repo;

    public PaymentServiceImpl(PaymentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Payment create(PaymentDTO dto) {
        Payment p = new Payment();

        p.setId(UUID.randomUUID().toString());
        p.setUserId(dto.getUserId());
        p.setPaymentMethod(dto.getPaymentMethod());
        p.setAmount(dto.getAmount());
        if (dto.getCurrency() != null) p.setCurrency(dto.getCurrency());
        if (dto.getPurpose() != null) p.setPurpose(dto.getPurpose());
        p.setTransactionId(dto.getTransactionId());
        p.setPaymentDetails(dto.getPaymentDetails());
        p.setReceiptUrl(dto.getReceiptUrl());

        return repo.save(p);
    }

    @Override
    public Payment updateStatus(String id, Payment.Status status) {
        Payment p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        p.setStatus(status);
        return repo.save(p);
    }

    @Override
    public List<Payment> getByUser(String userId) {
        return repo.findByUserId(userId);
    }

    @Override
    public Payment getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}
