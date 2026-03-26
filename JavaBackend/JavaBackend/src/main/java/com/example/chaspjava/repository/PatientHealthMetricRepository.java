package com.example.chaspjava.repository;

import com.example.chaspjava.entity.PatientHealthMetric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientHealthMetricRepository extends JpaRepository<PatientHealthMetric, String> {

    List<PatientHealthMetric> findByPatientIdOrderByRecordedAtDesc(String patientId);
}
