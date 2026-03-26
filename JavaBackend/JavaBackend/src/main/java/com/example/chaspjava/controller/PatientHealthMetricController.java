package com.example.chaspjava.controller;

import com.example.chaspjava.dto.PatientHealthMetricDTO;
import com.example.chaspjava.entity.PatientHealthMetric;
import com.example.chaspjava.service.PatientHealthMetricService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-metrics")
public class PatientHealthMetricController {

    private final PatientHealthMetricService service;

    public PatientHealthMetricController(PatientHealthMetricService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public PatientHealthMetric create(@RequestBody PatientHealthMetricDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/patient/{patientId}")
    public List<PatientHealthMetric> getByPatient(@PathVariable String patientId) {
        return service.getByPatient(patientId);
    }
}
