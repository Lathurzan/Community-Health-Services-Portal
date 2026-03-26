package com.example.chaspjava.controller;

import com.example.chaspjava.dto.HealthRecordDTO;
import com.example.chaspjava.entity.HealthRecord;
import com.example.chaspjava.service.HealthRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-records")
public class HealthRecordController {

    private final HealthRecordService service;

    public HealthRecordController(HealthRecordService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public HealthRecord create(@RequestBody HealthRecordDTO dto) {
        return service.create(dto);
    } 

    @GetMapping("/patient/{patientId}")
    public List<HealthRecord> getByPatient(@PathVariable String patientId) {
        return service.getByPatient(patientId);
    }

    @GetMapping("/provider/{providerId}")
    public List<HealthRecord> getByProvider(@PathVariable String providerId) {
        return service.getByProvider(providerId);
    }

    @GetMapping("/{id}")
    public HealthRecord getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/good-patients/count")
    public long countGoodPatients(@RequestParam(name = "minScore", defaultValue = "75") int minScore) {
        if (minScore < 0 || minScore > 100) throw new IllegalArgumentException("minScore must be between 0 and 100");
        return service.countGoodPatients(minScore);
    }
}
