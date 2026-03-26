package com.example.chaspjava.repository;

import com.example.chaspjava.entity.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthRecordRepository extends JpaRepository<HealthRecord, String> {

    List<HealthRecord> findByPatientId(String patientId);

    List<HealthRecord> findByProviderId(String providerId);
}
