package com.signup.fnc_bank.dto;

public class LoginResponse {

    private boolean success;
    private String message;
    private String email;
    private Integer userId;
    private String firstName;
    private String secondName;
    private String accountNumber;
    // Constructors
    public LoginResponse() {}


    public LoginResponse(boolean success, String message,  Integer userId, String email, String firstName, String secondName,String accountNumber) {
        this.success = success;
        this.message = message;
        this.email = email;
        this.userId = userId;
        this.firstName =firstName;
        this.secondName = secondName;
        this.accountNumber = accountNumber;
    }

    // Getters and Setters
    public boolean getSuccess() {
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



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getUserId() {
        return userId;
    }
    public String getAccountNumber() { return accountNumber; }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}