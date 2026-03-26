package com.example.chaspjava.repository;

import com.example.chaspjava.entity.DonationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationHistoryRepository extends JpaRepository<DonationHistory, String> {

    List<DonationHistory> findByPaymentId(String paymentId);
}
