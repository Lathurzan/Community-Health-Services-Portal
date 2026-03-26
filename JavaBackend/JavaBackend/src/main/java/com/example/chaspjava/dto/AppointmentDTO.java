package com.example.chaspjava.dto;

import com.example.chaspjava.entity.Appointment;
import java.time.LocalDateTime;

public class AppointmentDTO {

    private String patientId;
    private String providerId;
    private LocalDateTime appointmentDate;
    private Integer durationMinutes;
    private Appointment.AppointmentType appointmentType;
    private String notes;

    // GETTERS & SETTERS

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public Appointment.AppointmentType getAppointmentType() { return appointmentType; }
    public void setAppointmentType(Appointment.AppointmentType appointmentType) { this.appointmentType = appointmentType; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
