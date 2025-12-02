package com.signup.fnc_bank.dto;

public class AdminStats {
    private long totalUsers;
    private long totalTransactions;
    private long activeAccounts;
    private long pendingTransactions;

    public AdminStats(long totalUsers, long totalTransactions,
                      long activeAccounts, long pendingTransactions) {
        this.totalUsers = totalUsers;
        this.totalTransactions = totalTransactions;
        this.activeAccounts = activeAccounts;
        this.pendingTransactions = pendingTransactions;
    }

    // Getters and Setters
    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public long getActiveAccounts() { return activeAccounts; }
    public void setActiveAccounts(long activeAccounts) {
        this.activeAccounts = activeAccounts;
    }

    public long getPendingTransactions() { return pendingTransactions; }
    public void setPendingTransactions(long pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }
}
