package com.example.chaspjava.controller;

import com.example.chaspjava.dto.DonationHistoryDTO;
import com.example.chaspjava.entity.DonationHistory;
import com.example.chaspjava.service.DonationHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationHistoryController {

    private final DonationHistoryService service;

    public DonationHistoryController(DonationHistoryService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public DonationHistory create(@RequestBody DonationHistoryDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/payment/{paymentId}")
    public List<DonationHistory> getByPayment(@PathVariable String paymentId) {
        return service.getByPayment(paymentId);
    }
}
