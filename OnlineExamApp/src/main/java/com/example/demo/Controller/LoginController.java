package com.example.demo.Controller;

import com.example.demo.Model.Register;
import com.example.demo.Services.EmailService;
import com.example.demo.Services.LoginService;
import com.example.demo.Services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:5173")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        List<Register> user = loginService.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "❌ Email not registered!"));
        }

        String otp = otpService.generateOtp(email);
        loginService.updateOtpByEmail(email, otp);
        emailService.sendOtpEmail(email, otp);

        return ResponseEntity.ok(Map.of("message", "✅ OTP sent to your email!"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String username = body.get("username");
        String password = body.get("password");
        String otp = body.get("otp");

        if (!otpService.validateOtp(email, otp)) {
            return ResponseEntity.badRequest().body(Map.of("message", "❌ Invalid OTP!"));
        }

        List<Register> user = loginService.findByEmailAndUsernameAndPassword(email, username, password);
        if (user.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "❌ Incorrect credentials!"));
        }

//        return ResponseEntity.ok(Map.of("message", "✅ Login successful!"));
        
        Register student = user.get(0); // Get the matched student

        
        // ✅ Send student info back
        return ResponseEntity.ok(Map.of(
            "message", "✅ Login successful!",
            "name", student.getName(),
            "username", student.getUsername()
        ));
        
        
    }
    
    
}
