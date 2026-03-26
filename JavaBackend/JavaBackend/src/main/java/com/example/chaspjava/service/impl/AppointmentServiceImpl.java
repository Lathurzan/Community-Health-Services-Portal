package com.example.chaspjava.service.impl;

import com.example.chaspjava.dto.AppointmentDTO;
import com.example.chaspjava.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.chaspjava.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// Service layer implementation containing appointment business logic
@Service
public class AppointmentServiceImpl implements AppointmentService {
    // Repository abstraction used only within the service layer
    private final JpaRepository<Appointment, String> repo;
    // Constructor-based dependency injection of the repository
    public AppointmentServiceImpl(JpaRepository<Appointment, String> repo) {
        this.repo = repo;
    }
    // Create and persist a new appointment entity
    @Override
    public Appointment create(AppointmentDTO dto) {
        Appointment a = new Appointment();
        // Generate a unique identifier for the appointment
        a.setId(UUID.randomUUID().toString());
        // Map data from DTO to entity
        a.setPatientId(dto.getPatientId());
        a.setProviderId(dto.getProviderId());
        a.setAppointmentDate(dto.getAppointmentDate());
        a.setDurationMinutes(dto.getDurationMinutes());
        a.setAppointmentType(dto.getAppointmentType());
        a.setNotes(dto.getNotes());

        return repo.save(a);
    }

    @Override
    public List<Appointment> getByPatient(String patientId) {
    return repo.findAll()
        .stream()
        .filter(a -> patientId.equals(a.getPatientId()))
        .toList();
    }

    @Override
    public List<Appointment> getByProvider(String providerId) {
    return repo.findAll()
        .stream()
        .filter(a -> providerId.equals(a.getProviderId()))
        .toList();
    }

    @Override
    public Appointment getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    @Override
    public List<Appointment> getAll() {
        return repo.findAll();
    }

    @Override
    public List<Appointment> getTodayAppointments() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        return ((com.example.chaspjava.repository.AppointmentRepository) repo).findByAppointmentDateBetween(start, end);
    }

    @Override
    public List<Appointment> getTomorrowAppointments() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDateTime start = tomorrow.atStartOfDay();
        LocalDateTime end = tomorrow.atTime(LocalTime.MAX);
        return ((com.example.chaspjava.repository.AppointmentRepository) repo).findByAppointmentDateBetween(start, end);
    }

    @Override
    public List<Appointment> getUpcomingAppointments(int days) {
        if (days <= 0) throw new IllegalArgumentException("days must be positive");
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(days).atTime(LocalTime.MAX);
        return ((com.example.chaspjava.repository.AppointmentRepository) repo).findByAppointmentDateBetween(start, end);
    }

    @Override
    public long count() {
        return repo.count();
    }

    @Override
    public void delete(String id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Appointment not found");
        }
        repo.deleteById(id);
    }

    @Override
    public Appointment update(String id, AppointmentDTO dto) {
        Appointment existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (dto.getPatientId() != null) existing.setPatientId(dto.getPatientId());
        if (dto.getProviderId() != null) existing.setProviderId(dto.getProviderId());
        if (dto.getAppointmentDate() != null) existing.setAppointmentDate(dto.getAppointmentDate());
        if (dto.getDurationMinutes() != null) existing.setDurationMinutes(dto.getDurationMinutes());
        if (dto.getAppointmentType() != null) existing.setAppointmentType(dto.getAppointmentType());
        if (dto.getNotes() != null) existing.setNotes(dto.getNotes());

        return repo.save(existing);
    }
}
