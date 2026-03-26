package com.example.chaspjava.repository;

import com.example.chaspjava.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, String> {

    List<Medication> findByPatientId(String patientId);

    List<Medication> findByProviderId(String providerId);
}
