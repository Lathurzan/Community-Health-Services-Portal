package com.example.chaspjava.service.impl;

import com.example.chaspjava.dto.PatientHealthMetricDTO;
import com.example.chaspjava.entity.PatientHealthMetric;
import com.example.chaspjava.repository.PatientHealthMetricRepository;
import com.example.chaspjava.service.PatientHealthMetricService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientHealthMetricServiceImpl implements PatientHealthMetricService {

    private final PatientHealthMetricRepository repo;

    public PatientHealthMetricServiceImpl(PatientHealthMetricRepository repo) {
        this.repo = repo;
    }

    @Override
    public PatientHealthMetric create(PatientHealthMetricDTO dto) {
        PatientHealthMetric m = new PatientHealthMetric();
        m.setId(UUID.randomUUID().toString());
        m.setPatientId(dto.getPatientId());
        m.setHealthScore(dto.getHealthScore());
        if (dto.getMetricsData() != null) m.setMetricsData(dto.getMetricsData());
        if (dto.getRecordedAt() != null) m.setRecordedAt(dto.getRecordedAt());
        return repo.save(m);
    }

    @Override
    public List<PatientHealthMetric> getByPatient(String patientId) {
        return repo.findByPatientIdOrderByRecordedAtDesc(patientId);
    }
}
