package com.example.demo.Services;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
//@RequiredArgsConstructor
public class OtpService {
    
    private final JavaMailSender mailSender;
    public OtpService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Secure OTP Generation (6-digit)
    public String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000); // Ensures 6-digit OTP
        return String.valueOf(otp);
    }

    // Send OTP via Email
    public boolean sendOtp(String email, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Your OTP for Registration");
            message.setText("Your OTP code is: " + otp + "\n\nThis OTP is valid for 5 minutes.");

            mailSender.send(message);
            log.info("OTP sent successfully to {}", email);
            return true; // Successfully sent
        } catch (Exception e) {
            log.error("Error sending OTP to {}: {}", email, e.getMessage());
            return false; // Failed to send
        }
    }
}
