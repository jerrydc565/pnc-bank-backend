package com.signup.fnc_bank.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.signup.fnc_bank.model.TransactionStatus;
import com.signup.fnc_bank.model.TransactionType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionId")
    private Long id;

    @Column(nullable = false, name = "UserId")
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column( name = "TransactionType" , nullable = false)
    private TransactionType transactionType;

    @Column( name = "Amount" ,nullable = false)
    private Double amount;



    @Column( name = "Description")
    private String description;

    @Column( name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;



    @Column(name = "BalanceAfter", nullable = false)  // ✅ Match SQL Server
    private Double balanceAfter;


    @Enumerated(EnumType.STRING)
    @Column( name = "status" , nullable = false)
    private TransactionStatus status = TransactionStatus.APPROVED;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(name = "approved_by")
    private Long approvedBy;

    // Getters and setters
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

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Double getBalanceAfter() { return balanceAfter; }
    public void setBalanceAfter(Double balanceAfter) { this.balanceAfter = balanceAfter; }

    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }

    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }

    public LocalDateTime getRejectedAt() { return rejectedAt; }
    public void setRejectedAt(LocalDateTime rejectedAt) { this.rejectedAt = rejectedAt; }

    public Long getApprovedBy() { return approvedBy; }
    public void setApprovedBy(Long approvedBy) { this.approvedBy = approvedBy; }
}


