package com.example.chaspjava.repository;

import com.example.chaspjava.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

    List<Subscription> findByPatientId(String patientId);

    Optional<Subscription> findByPatientIdAndSubscriptionType(String patientId, Subscription.SubscriptionType subscriptionType);
}
