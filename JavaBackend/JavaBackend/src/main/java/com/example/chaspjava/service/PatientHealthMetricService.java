package com.example.chaspjava.service;

import com.example.chaspjava.dto.PatientHealthMetricDTO;
import com.example.chaspjava.entity.PatientHealthMetric;

import java.util.List;

public interface PatientHealthMetricService {

    PatientHealthMetric create(PatientHealthMetricDTO dto);

    List<PatientHealthMetric> getByPatient(String patientId);
}
