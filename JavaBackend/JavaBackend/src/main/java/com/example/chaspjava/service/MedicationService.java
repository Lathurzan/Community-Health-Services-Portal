package com.example.chaspjava.service;

import com.example.chaspjava.dto.MedicationDTO;
import com.example.chaspjava.entity.Medication;

import java.util.List;

public interface MedicationService {

    Medication create(MedicationDTO dto);

    List<Medication> getByPatient(String patientId);

    List<Medication> getByProvider(String providerId);

    Medication markInactive(String id);

    Medication getById(String id);
}
