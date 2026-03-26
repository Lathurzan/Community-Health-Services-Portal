package com.example.chaspjava.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "patient_id", length = 36, nullable = false)
    private String patientId;

    @Column(name = "provider_id", length = 36, nullable = false)
    private String providerId;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @Column(name = "duration_minutes")
    private Integer durationMinutes = 30;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.scheduled;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_type", nullable = false)
    private AppointmentType appointmentType;

    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Status {
        scheduled,
        completed,
        cancelled,
        no_show     // FIXED
    }

    public enum AppointmentType {
        checkup,
        consultation,
        follow_up,  // FIXED
        emergency
        ;

        @JsonCreator
        public static AppointmentType fromString(String key) {
            if (key == null) return null;
            String k = key.trim().toLowerCase().replace('-', '_').replace(' ', '_').replaceAll("\\d", "");
            if (k.equals("followup")) k = "follow_up";
            for (AppointmentType t : values()) {
                if (t.name().equalsIgnoreCase(k)) return t;
            }
            throw new IllegalArgumentException("Invalid appointment type: " + key);
        }
    }

    @PrePersist
    public void beforeCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void beforeUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // GETTERS & SETTERS

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public AppointmentType getAppointmentType() { return appointmentType; }
    public void setAppointmentType(AppointmentType appointmentType) { this.appointmentType = appointmentType; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
