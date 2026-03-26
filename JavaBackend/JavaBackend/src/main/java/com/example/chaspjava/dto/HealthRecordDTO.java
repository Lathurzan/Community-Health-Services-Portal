package com.example.chaspjava.dto;

import com.example.chaspjava.entity.HealthRecord;

public class HealthRecordDTO {

    private String patientId;
    private String providerId;
    private HealthRecord.RecordType recordType;
    private String title;
    private String description;

    // GETTERS & SETTERS

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public HealthRecord.RecordType getRecordType() { return recordType; }
    public void setRecordType(HealthRecord.RecordType recordType) { this.recordType = recordType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
