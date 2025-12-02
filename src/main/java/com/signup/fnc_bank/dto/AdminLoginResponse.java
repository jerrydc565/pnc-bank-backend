package com.signup.fnc_bank.dto;

public class AdminLoginResponse {
    private String token;
    private Long adminId;
    private String username;

    public AdminLoginResponse(String token, Long adminId, String username) {
        this.token = token;
        this.adminId = adminId;
        this.username = username;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getAdminId() { return adminId; }
    public void setAdminId(Long adminId) { this.adminId = adminId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
