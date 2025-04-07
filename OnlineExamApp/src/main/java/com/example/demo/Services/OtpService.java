package com.example.demo.Services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OtpService {

    private final Map<String, String> otpStore = new HashMap<>();

    public String generateOtp(String key) {
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        otpStore.put(key, otp);
        return otp;
    }

    public boolean validateOtp(String key, String otp) {
        return otp.equals(otpStore.get(key));
    }
}
