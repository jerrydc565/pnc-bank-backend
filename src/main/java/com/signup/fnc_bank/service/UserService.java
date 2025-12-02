package com.signup.fnc_bank.service;


import com.signup.fnc_bank.dto.*;
import com.signup.fnc_bank.model.User;
import com.signup.fnc_bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public SignupResponse registerUser(SignupRequest request) {

        // Validate input
        if (request.getFirstName() == null || request.getFirstName().trim().isEmpty()) {
            return new SignupResponse(false, "First name is required");
        }

        if (request.getSecondName() == null || request.getSecondName().trim().isEmpty()) {
            return new SignupResponse(false, "Second name is required");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return new SignupResponse(false, "Email is required");
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            return new SignupResponse(false, "Password is required");
        }


        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return new SignupResponse(false, "Email already registered");
        }

        try {
            // Create new user
            User newUser = new User(
                    request.getFirstName(),
                    request.getSecondName(),
                    request.getEmail(),
                    request.getPassword()  // In production, hash this password!
            );
            newUser.setAccountNumber(generateAccountNumber());

            System.out.println("Creating user:");
            System.out.println("  FirstName: " + request.getFirstName());
            System.out.println("  SecondName: " + request.getSecondName());
            System.out.println("  Email: " + request.getEmail());

            // Save to database
            userRepository.save(newUser);

            return new SignupResponse(true, "User registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return new SignupResponse(false, "Registration failed: " + e.getMessage());
        }
    }
    private String generateAccountNumber() {
        // Generate a unique 16-digit account number
        // Format: YYYYMM + 8-digit random + 2-digit checksum
        String yearMonth = String.format("%04d%02d",
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue());

        String randomPart = String.format("%08d",
                (int)(Math.random() * 100000000));

        String checksum = String.format("%02d",
                (int)(Math.random() * 100));

        return yearMonth + randomPart + checksum;
    }
    public LoginResponse loginUser(LoginRequest request) {
        try {  // ✅ ADD THIS TRY BLOCK
            User user = userRepository.findByEmail(request.getEmail());

            if (user != null && user.getPassword().equals(request.getPassword())) {  // ✅ Fixed: getPasswords()
                return new LoginResponse(
                        true,
                        "Login successful",
                        user.getUserId(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getSecondName(),
                        user.getAccountNumber()
                );
            }

            return new LoginResponse(false, "Invalid email or password", null, null, null, null,null);

        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse(false, "Login failed: " + e.getMessage(), null, null, null, null,null);
        }
    }

    public UserProfileResponse getUserProfile(String email) {

        System.out.println("========== GET USER PROFILE ==========");
        System.out.println("Looking for email: " + email); // ✅ Debug log

        try {
            // Find user by email
            User user = userRepository.findByEmail(email);

            System.out.println("Found user: " + (user != null ? user.getEmail() : "NULL")); // ✅ Debug log

            // Check if user exists
            if (user == null) {
                System.out.println("❌ User not found for email: " + email);
                return new UserProfileResponse(false, "User not found");
            }

            System.out.println("Found user:");
            System.out.println("  FirstName: " + user.getFirstName());
            System.out.println("  SecondName: " + user.getSecondName());
            System.out.println("  Email: " + user.getEmail());
            // Return user profile
            System.out.println("✅ Returning profile for: " + user.getFirstName());
            return new UserProfileResponse(
                    true,
                    "User profile retrieved successfully",
                    user.getUserId(),
                    user.getFirstName(),
                    user.getSecondName(),
                    user.getEmail(),
                    user.getCreatedAt()
            );

        } catch (Exception e) {
            System.out.println("❌ Exception: " + e.getMessage());
            e.printStackTrace();
            return new UserProfileResponse(false, "Error retrieving profile: " + e.getMessage());
        }
    }

    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {

        // Validate input
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return new ForgotPasswordResponse(false, "Email is required");
        }

        try {
            // Find user by email
            User user = userRepository.findByEmail(request.getEmail());

            // Check if user exists
            if (user == null) {
                return new ForgotPasswordResponse(false, "No account found with this email");
            }

            // Return the password
            // Note: In production, you should send a reset link via email instead!
            return new ForgotPasswordResponse(
                    true,
                    "Password retrieved successfully!",
                    user.getPassword()
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new ForgotPasswordResponse(false, "Error retrieving password: " + e.getMessage());
        }

    }
}

