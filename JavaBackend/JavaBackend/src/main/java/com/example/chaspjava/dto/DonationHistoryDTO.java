package com.example.chaspjava.dto;

import com.example.chaspjava.entity.DonationHistory;

public class DonationHistoryDTO {

    private String paymentId;
    private String donorName;
    private String donorEmail;
    private String message;
    private DonationHistory.Visibility visibility;

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getDonorName() { return donorName; }
    public void setDonorName(String donorName) { this.donorName = donorName; }

    public String getDonorEmail() { return donorEmail; }
    public void setDonorEmail(String donorEmail) { this.donorEmail = donorEmail; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public DonationHistory.Visibility getVisibility() { return visibility; }
    public void setVisibility(DonationHistory.Visibility visibility) { this.visibility = visibility; }
}
