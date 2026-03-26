package com.example.chaspjava.service;

import com.example.chaspjava.dto.SubscriptionDTO;
import com.example.chaspjava.entity.Subscription;

import java.util.List;

public interface SubscriptionService {

    Subscription createOrUpdate(SubscriptionDTO dto);

    List<Subscription> getByPatient(String patientId);

    List<Subscription> getAll();

    /**
     * Return total number of subscriptions.
     */
    long count();
}
