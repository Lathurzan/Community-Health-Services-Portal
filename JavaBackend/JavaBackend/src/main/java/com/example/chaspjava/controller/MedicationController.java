package com.example.chaspjava.controller;

import com.example.chaspjava.dto.MedicationDTO;
import com.example.chaspjava.entity.Medication;
import com.example.chaspjava.service.MedicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    private final MedicationService service;

    public MedicationController(MedicationService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Medication create(@RequestBody MedicationDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/patient/{patientId}")
    public List<Medication> getByPatient(@PathVariable String patientId) {
        return service.getByPatient(patientId);
    }

    @GetMapping("/provider/{providerId}")
    public List<Medication> getByProvider(@PathVariable String providerId) {
        return service.getByProvider(providerId);
    }

    @PutMapping("/deactivate/{id}")
    public Medication deactivate(@PathVariable String id) {
        return service.markInactive(id);
    }

    @GetMapping("/{id}")
    public Medication getById(@PathVariable String id) {
        return service.getById(id);
    }
}
