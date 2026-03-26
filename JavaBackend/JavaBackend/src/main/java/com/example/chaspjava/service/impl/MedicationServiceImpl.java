package com.example.chaspjava.service.impl;

import com.example.chaspjava.dto.MedicationDTO;
import com.example.chaspjava.entity.Medication;
import com.example.chaspjava.repository.MedicationRepository;
import com.example.chaspjava.service.MedicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository repo;

    public MedicationServiceImpl(MedicationRepository repo) {
        this.repo = repo;
    }

    @Override
    public Medication create(MedicationDTO dto) {
        Medication m = new Medication();

        m.setId(UUID.randomUUID().toString());
        m.setPatientId(dto.getPatientId());
        m.setProviderId(dto.getProviderId());
        m.setMedicationName(dto.getMedicationName());
        m.setDosage(dto.getDosage());
        m.setFrequency(dto.getFrequency());
        m.setNotes(dto.getNotes());

        return repo.save(m);
    }

    @Override
    public List<Medication> getByPatient(String patientId) {
        return repo.findByPatientId(patientId);
    }

    @Override
    public List<Medication> getByProvider(String providerId) {
        return repo.findByProviderId(providerId);
    }

    @Override
    public Medication markInactive(String id) {
        Medication med = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medication not found"));
        med.setActive(false);
        return repo.save(med);
    }

    @Override
    public Medication getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medication not found"));
    }
}
