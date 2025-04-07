package com.example.demo.Controller;
import com.example.demo.Services.ForgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class ForgetPassword {

	    @Autowired
	    private ForgetService forgetService;

	    @PostMapping("/forgot-password")
	    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
	        String email = request.get("email");
	        String newPassword = request.get("newPassword");

	        String message = forgetService.updatePassword(email, newPassword);
	        return ResponseEntity.ok(message);
	    }
	}
