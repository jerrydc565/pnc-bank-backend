package com.signup.fnc_bank.model;



import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OtpStore {
    private static final Map<String, OtpData> otpStorage = new HashMap<>();

    public static void storeOtp(String email, String otp) {
        otpStorage.put(email, new OtpData(otp, LocalDateTime.now().plusMinutes(5)));
    }

    public static boolean verifyOtp(String email, String otp) {
        OtpData data = otpStorage.get(email);
        if (data == null) {
            return false;
        }

        if (LocalDateTime.now().isAfter(data.expirationTime)) {
            otpStorage.remove(email);
            return false;
        }

        if (data.otp.equals(otp)) {
            otpStorage.remove(email);
            return true;
        }

        return false;
    }

    static class OtpData {
        String otp;
        LocalDateTime expirationTime;

        OtpData(String otp, LocalDateTime expirationTime) {
            this.otp = otp;
            this.expirationTime = expirationTime;
        }
    }
}