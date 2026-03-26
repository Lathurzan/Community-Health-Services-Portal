package com.example.chaspjava.service.impl;

import com.example.chaspjava.dto.SubscriptionDTO;
import com.example.chaspjava.entity.Subscription;
import com.example.chaspjava.repository.SubscriptionRepository;
import com.example.chaspjava.service.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository repo;

    public SubscriptionServiceImpl(SubscriptionRepository repo) {
        this.repo = repo;
    }

    @Override
    public Subscription createOrUpdate(SubscriptionDTO dto) {
        Subscription sub = repo
                .findByPatientIdAndSubscriptionType(dto.getPatientId(), dto.getSubscriptionType())
                .orElseGet(Subscription::new);

        if (sub.getId() == null) {
            sub.setId(UUID.randomUUID().toString());
            sub.setPatientId(dto.getPatientId());
            sub.setSubscriptionType(dto.getSubscriptionType());
        }

        if (dto.getIsActive() != null) sub.setIsActive(dto.getIsActive());
        if (dto.getPreferences() != null) sub.setPreferences(dto.getPreferences());

        return repo.save(sub);
    }

    @Override
    public List<Subscription> getByPatient(String patientId) {
        return repo.findByPatientId(patientId);
    }

    @Override
    public List<Subscription> getAll() {
        return repo.findAll();
    }

    @Override
    public long count() {
        return repo.count();
    }
}
