package com.signup.fnc_bank.dto;



import java.time.LocalDateTime;

public class UserProfileResponse {

    private boolean success;
    private String message;
    private Integer userId;
    private String firstName;
    private String secondName;
    private String email;
    private LocalDateTime createdAt;

    // Constructors
    public UserProfileResponse() {}

    public UserProfileResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public UserProfileResponse(boolean success, String message, Integer userId,
                               String firstName, String secondName, String email, LocalDateTime createdAt) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.createdAt = createdAt;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName (){
        return secondName;
    }

    public void setSecondName ( String secondName){
        this.secondName = secondName ;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}