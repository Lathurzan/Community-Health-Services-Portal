package com.example.chaspjava.service;

import com.example.chaspjava.dto.DonationHistoryDTO;
import com.example.chaspjava.entity.DonationHistory;

import java.util.List;

public interface DonationHistoryService {

    DonationHistory create(DonationHistoryDTO dto);

    List<DonationHistory> getByPayment(String paymentId);
}
