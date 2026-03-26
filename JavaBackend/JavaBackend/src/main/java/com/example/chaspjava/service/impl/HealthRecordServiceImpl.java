package com.example.chaspjava.service.impl;

import com.example.chaspjava.dto.HealthRecordDTO;
import com.example.chaspjava.entity.HealthRecord;
import com.example.chaspjava.entity.PatientHealthMetric;
import com.example.chaspjava.repository.HealthRecordRepository;
import com.example.chaspjava.repository.PatientHealthMetricRepository;
import com.example.chaspjava.service.HealthRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class HealthRecordServiceImpl implements HealthRecordService {

    private final HealthRecordRepository repo;
    private final PatientHealthMetricRepository metricRepo;

    public HealthRecordServiceImpl(HealthRecordRepository repo, PatientHealthMetricRepository metricRepo) {
        this.repo = repo;
        this.metricRepo = metricRepo;
    }

    @Override
    public HealthRecord create(HealthRecordDTO dto) {
        HealthRecord r = new HealthRecord();

        r.setId(UUID.randomUUID().toString());
        r.setPatientId(dto.getPatientId());
        r.setProviderId(dto.getProviderId());
        r.setRecordType(dto.getRecordType());
        r.setTitle(dto.getTitle());
        r.setDescription(dto.getDescription());

        return repo.save(r);
    }

    @Override
    public List<HealthRecord> getByPatient(String patientId) {
        return repo.findByPatientId(patientId);
    }

    @Override
    public List<HealthRecord> getByProvider(String providerId) {
        return repo.findByProviderId(providerId);
    }

    @Override
    public HealthRecord getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Health record not found"));
    }

    @Override
    public long countGoodPatients(int minScore) {
        List<PatientHealthMetric> all = metricRepo.findAll();
        Map<String, PatientHealthMetric> latestByPatient = new HashMap<>();

        for (PatientHealthMetric m : all) {
            if (m == null || m.getPatientId() == null) continue;
            PatientHealthMetric current = latestByPatient.get(m.getPatientId());
            if (current == null) {
                latestByPatient.put(m.getPatientId(), m);
                continue;
            }
            LocalDateTime curTime = current.getRecordedAt();
            LocalDateTime newTime = m.getRecordedAt();
            if (newTime != null && (curTime == null || newTime.isAfter(curTime))) {
                latestByPatient.put(m.getPatientId(), m);
            }
        }

        return latestByPatient.values().stream()
                .filter(x -> x.getHealthScore() != null && x.getHealthScore() >= minScore)
                .count();
    }
}
