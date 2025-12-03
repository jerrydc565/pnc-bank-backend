package com.signup.fnc_bank.controller;


import com.signup.fnc_bank.model.OtpStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OtpController {

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, Object>> sendOtp(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String email = request.get("email");

            if (email == null || email.isEmpty()) {
                response.put("success", false);
                response.put("message", "Email is required");
                return ResponseEntity.badRequest().body(response);
            }

            // Generate 6-digit OTP
            String otp = String.format("%06d", new Random().nextInt(1000000));

            // Store OTP (expires in 5 minutes)
            OtpStore.storeOtp(email, otp);

            // TODO: Send OTP via email (you'll need to implement email service)
            // For now, we'll just print it to console
            System.out.println("===========================================");
            System.out.println("OTP for " + email + ": " + otp);
            System.out.println("===========================================");

            response.put("success", true);
            response.put("message", "OTP sent to your email");
            response.put("otp", otp); // REMOVE THIS IN PRODUCTION - only for testing

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error sending OTP: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String email = request.get("email");
            String otp = request.get("otp");

            if (email == null || email.isEmpty() || otp == null || otp.isEmpty()) {
                response.put("success", false);
                response.put("message", "Email and OTP are required");
                return ResponseEntity.badRequest().body(response);
            }

            boolean isValid = OtpStore.verifyOtp(email, otp);

            if (isValid) {
                response.put("success", true);
                response.put("message", "OTP verified successfully");
            } else {
                response.put("success", false);
                response.put("message", "Invalid or expired OTP");
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error verifying OTP: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}