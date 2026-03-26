package com.example.chaspjava.dto;

import com.example.chaspjava.entity.Subscription;

public class SubscriptionDTO {

    private String patientId;
    private Subscription.SubscriptionType subscriptionType;
    private Boolean isActive;
    private String preferences;

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public Subscription.SubscriptionType getSubscriptionType() { return subscriptionType; }
    public void setSubscriptionType(Subscription.SubscriptionType subscriptionType) { this.subscriptionType = subscriptionType; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { isActive = active; }

    public String getPreferences() { return preferences; }
    public void setPreferences(String preferences) { this.preferences = preferences; }
}
