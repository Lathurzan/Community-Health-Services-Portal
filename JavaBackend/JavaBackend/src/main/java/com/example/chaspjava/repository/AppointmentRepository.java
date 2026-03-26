package com.example.chaspjava.repository;

import com.example.chaspjava.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    List<Appointment> findByPatientId(String patientId);

    List<Appointment> findByProviderId(String providerId);

    List<Appointment> findByAppointmentDateBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);
}
