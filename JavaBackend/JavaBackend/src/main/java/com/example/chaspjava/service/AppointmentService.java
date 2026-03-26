package com.example.chaspjava.service;

import com.example.chaspjava.dto.AppointmentDTO;
import com.example.chaspjava.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment create(AppointmentDTO dto);

    List<Appointment> getByPatient(String patientId);

    List<Appointment> getByProvider(String providerId);

    Appointment getById(String id);

    List<Appointment> getAll();
    long count();
    List<Appointment> getTodayAppointments();
    List<Appointment> getTomorrowAppointments();
    List<Appointment> getUpcomingAppointments(int days);
    /**
     * Delete appointment by id. Throws RuntimeException if not found.
     */
    void delete(String id);
    /**
     * Update appointment identified by id with values from dto. Returns updated entity.
     */
    Appointment update(String id, AppointmentDTO dto);
}
