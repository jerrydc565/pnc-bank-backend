package com.signup.fnc_bank.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${RESEND_API_KEY:}")
    private String resendApiKey;

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();

    @Async
    public void sendOtpEmail(String toEmail, String otp) {
        try {
            System.out.println("=================================");
            System.out.println("Attempting to send OTP email to: " + toEmail);
            System.out.println("Using Resend API");
            System.out.println("=================================");
            
            String json = String.format(
                "{\"from\":\"PNC Bank <onboarding@resend.dev>\",\"to\":[\"%s\"],\"subject\":\"PNC Bank - Your Login Verification Code\",\"html\":\"<h2>Hello,</h2><p>Your One-Time Password (OTP) for PNC Bank login is:</p><h1 style='color: #0066cc; font-size: 32px;'>%s</h1><p>This code will expire in 5 minutes.</p><p>If you did not request this code, please ignore this email.</p><p>Thank you,<br>PNC Bank Security Team</p>\"}",
                toEmail, otp
            );

            System.out.println("Request JSON: " + json);

            RequestBody body = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                .url("https://api.resend.com/emails")
                .addHeader("Authorization", "Bearer " + resendApiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();
                System.out.println("Response code: " + response.code());
                System.out.println("Response body: " + responseBody);
                
                if (response.isSuccessful()) {
                    System.out.println("=================================");
                    System.out.println("SUCCESS: OTP email sent to: " + toEmail);
                    System.out.println("=================================");
                } else {
                    System.err.println("=================================");
                    System.err.println("FAILED to send OTP email to " + toEmail);
                    System.err.println("Response code: " + response.code());
                    System.err.println("Response body: " + responseBody);
                    System.err.println("=================================");
                }
            }
        } catch (Exception e) {
            System.err.println("=================================");
            System.err.println("EXCEPTION while sending OTP email to " + toEmail);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.err.println("=================================");
        }
    }
}
