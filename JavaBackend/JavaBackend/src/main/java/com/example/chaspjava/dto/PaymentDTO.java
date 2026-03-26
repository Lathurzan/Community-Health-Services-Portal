package com.example.chaspjava.dto;

import com.example.chaspjava.entity.Payment;

import java.math.BigDecimal;

public class PaymentDTO {

    private String userId;
    private Payment.PaymentMethod paymentMethod;
    private BigDecimal amount;
    private String currency;
    private Payment.Purpose purpose;
    private String transactionId;
    private String paymentDetails;
    private String receiptUrl;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Payment.PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(Payment.PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public Payment.Purpose getPurpose() { return purpose; }
    public void setPurpose(Payment.Purpose purpose) { this.purpose = purpose; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getPaymentDetails() { return paymentDetails; }
    public void setPaymentDetails(String paymentDetails) { this.paymentDetails = paymentDetails; }

    public String getReceiptUrl() { return receiptUrl; }
    public void setReceiptUrl(String receiptUrl) { this.receiptUrl = receiptUrl; }
}
