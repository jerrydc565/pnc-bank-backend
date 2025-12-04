package com.signup.fnc_bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendOtpEmail(String toEmail, String otp) {
        try {
            System.out.println("Attempting to send OTP email to: " + toEmail);
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("pnconlinebanking67@gmail.com");
            message.setTo(toEmail);
            message.setSubject("PNC Bank - Your Login Verification Code");
            message.setText(
                "Hello,\n\n" +
                "Your One-Time Password (OTP) for PNC Bank login is:\n\n" +
                otp + "\n\n" +
                "This code will expire in 5 minutes.\n\n" +
                "If you did not request this code, please ignore this email.\n\n" +
                "Thank you,\n" +
                "PNC Bank Security Team"
            );

            mailSender.send(message);
            System.out.println("SUCCESS: OTP email sent to: " + toEmail);
        } catch (Exception e) {
            System.err.println("FAILED to send OTP email to " + toEmail);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
