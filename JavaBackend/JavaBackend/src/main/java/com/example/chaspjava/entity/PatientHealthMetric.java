package com.example.chaspjava.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_health_metrics")
public class PatientHealthMetric {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "patient_id", length = 36, nullable = false)
    private String patientId;

    @Column(name = "health_score", nullable = false)
    private Integer healthScore;

    @Column(name = "metrics_data", columnDefinition = "JSON")
    private String metricsData = "{\"blood_pressure\": null, \"heart_rate\": null, \"weight\": null, \"sleep_hours\": null, \"exercise_minutes\": null}";

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt = LocalDateTime.now();

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // GETTERS & SETTERS

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public Integer getHealthScore() { return healthScore; }
    public void setHealthScore(Integer healthScore) { this.healthScore = healthScore; }

    public String getMetricsData() { return metricsData; }
    public void setMetricsData(String metricsData) { this.metricsData = metricsData; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
