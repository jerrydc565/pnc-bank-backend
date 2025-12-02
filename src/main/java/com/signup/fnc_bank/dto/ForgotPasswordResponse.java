package com.signup.fnc_bank.dto;

public class ForgotPasswordResponse {

    private boolean success;
    private String message;
    private String password; // Send password back (only for this simple version)

    // Constructors
    public ForgotPasswordResponse() {}

    public ForgotPasswordResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ForgotPasswordResponse(boolean success, String message, String password) {
        this.success = success;
        this.message = message;
        this.password = password;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
