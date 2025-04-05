package com.example.demo.Controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Otp;
import com.example.demo.Model.User;
import com.example.demo.Repository.OtpRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Services.OtpService;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:5173")	
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private OtpService otpService;
    
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = otpService.generateOtp();

        Otp otpEntity = new Otp(email, otp, LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otpEntity);
        
        System.out.println("Generated OTP for " + email + ": " + otp); // Debugging
        
        otpService.sendOtp(email, otp);
        return ResponseEntity.ok("OTP Sent!");
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String enteredOtp = request.get("otp");

        Optional<Otp> otpEntity = otpRepository.findTopByEmailOrderByExpiryTimeDesc(email);

        
        if (otpEntity.isPresent()) {
            System.out.println("Stored OTP: " + otpEntity.get().getOtpCode()); // Debugging
            System.out.println("Entered OTP: " + enteredOtp); // Debugging

            if (otpEntity.get().getOtpCode().equals(enteredOtp)) {
                if (otpEntity.get().getExpiryTime().isBefore(LocalDateTime.now())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP Expired!");
                }

                // Register the user after successful OTP verification
                User user = new User(email, request.get("password"));
                user.setVerified(true);
                userRepository.save(user);

                return ResponseEntity.ok("User Registered Successfully!");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP!");
    }

    

    
}

