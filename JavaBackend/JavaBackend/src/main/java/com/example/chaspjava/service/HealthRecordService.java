package com.example.chaspjava.service;

import com.example.chaspjava.dto.HealthRecordDTO;
import com.example.chaspjava.entity.HealthRecord;

import java.util.List;

public interface HealthRecordService {

    HealthRecord create(HealthRecordDTO dto);

    List<HealthRecord> getByPatient(String patientId);

    List<HealthRecord> getByProvider(String providerId);

    HealthRecord getById(String id);
    
    /**
     * Count distinct patients whose latest recorded health metric has a health score
     * greater than or equal to the provided minimum score.
     * @param minScore minimum health score (0-100)
     */
    long countGoodPatients(int minScore);
}
