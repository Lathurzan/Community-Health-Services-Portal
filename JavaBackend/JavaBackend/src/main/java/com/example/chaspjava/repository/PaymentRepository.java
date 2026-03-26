package com.example.chaspjava.repository;

import com.example.chaspjava.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    List<Payment> findByUserId(String userId);

    List<Payment> findByStatus(Payment.Status status);
}
