package com.signup.fnc_bank.dto;

import com.signup.fnc_bank.model.TransactionType;
import java.time.LocalDateTime;

public class TransactionResponse {
    private Long id;
    private Integer userId;
    private TransactionType transactionType;
    private Double amount;
    private String description;
    private LocalDateTime createdAt;
    private Double balanceAfter;

    // Constructors
    public TransactionResponse() {}

    public TransactionResponse(Long id, Integer userId, TransactionType transactionType,
                               Double amount, String description, LocalDateTime createdAt,
                               Double balanceAfter) {
        this.id = id;
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
}