package com.example.chaspjava.dto;

public class MedicationDTO {

    private String patientId;
    private String providerId;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String notes;

    // GETTERS & SETTERS

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public String getMedicationName() { return medicationName; }
    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
