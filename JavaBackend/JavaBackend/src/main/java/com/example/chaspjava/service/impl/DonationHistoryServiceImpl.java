package com.example.chaspjava.service.impl;

import com.example.chaspjava.dto.DonationHistoryDTO;
import com.example.chaspjava.entity.DonationHistory;
import com.example.chaspjava.repository.DonationHistoryRepository;
import com.example.chaspjava.service.DonationHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DonationHistoryServiceImpl implements DonationHistoryService {

    private final DonationHistoryRepository repo;

    public DonationHistoryServiceImpl(DonationHistoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public DonationHistory create(DonationHistoryDTO dto) {
        DonationHistory d = new DonationHistory();
        d.setId(UUID.randomUUID().toString());
        d.setPaymentId(dto.getPaymentId());
        d.setDonorName(dto.getDonorName());
        d.setDonorEmail(dto.getDonorEmail());
        d.setMessage(dto.getMessage());
        if (dto.getVisibility() != null) d.setVisibility(dto.getVisibility());
        return repo.save(d);
    }

    @Override
    public List<DonationHistory> getByPayment(String paymentId) {
        return repo.findByPaymentId(paymentId);
    }
}
