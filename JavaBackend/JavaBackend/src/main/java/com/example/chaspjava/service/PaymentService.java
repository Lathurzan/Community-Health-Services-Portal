package com.example.chaspjava.service;

import com.example.chaspjava.dto.PaymentDTO;
import com.example.chaspjava.entity.Payment;

import java.util.List;

public interface PaymentService {

    Payment create(PaymentDTO dto);

    Payment updateStatus(String id, Payment.Status status);

    List<Payment> getByUser(String userId);

    Payment getById(String id);
}
