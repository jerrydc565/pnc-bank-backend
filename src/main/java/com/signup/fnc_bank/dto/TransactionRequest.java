package com.signup.fnc_bank.dto;


import com.signup.fnc_bank.model.TransactionType;

public class TransactionRequest {
    private Integer userId;
    private TransactionType transactionType;
    private Double amount;
    private String description;

    // Constructors
    public TransactionRequest() {}

    public TransactionRequest(Integer userId, TransactionType transactionType, Double amount, String description) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}