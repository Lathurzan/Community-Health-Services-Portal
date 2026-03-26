package com.example.chaspjava.dto;

import java.time.LocalDateTime;

public class PatientHealthMetricDTO {

    private String patientId;
    private Integer healthScore;
    private String metricsData;
    private LocalDateTime recordedAt;

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public Integer getHealthScore() { return healthScore; }
    public void setHealthScore(Integer healthScore) { this.healthScore = healthScore; }

    public String getMetricsData() { return metricsData; }
    public void setMetricsData(String metricsData) { this.metricsData = metricsData; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}
