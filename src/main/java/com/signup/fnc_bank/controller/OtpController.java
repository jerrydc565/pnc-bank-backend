package com.signup.fnc_bank.controller;


import com.signup.fnc_bank.model.OtpStore;
import com.signup.fnc_bank.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OtpController {

    private final OtpStore otpStore = new OtpStore();
    @Autowired
    private EmailService emailService;

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, Object>> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Store OTP
        otpStore.storeOtp(email, otp);

        // Send OTP to email
        emailService.sendOtpEmail(email, otp);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "OTP sent to your email");

        System.out.println("OTP sent to " + email + ": " + otp); // Keep for debugging

        return ResponseEntity.ok(response);
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        boolean isValid = otpStore.verifyOtp(email, otp);

        Map<String, Object> response = new HashMap<>();
        response.put("success", isValid);
        response.put("message", isValid ? "OTP verified successfully" : "Invalid or expired OTP");

        return ResponseEntity.ok(response);
    }
}