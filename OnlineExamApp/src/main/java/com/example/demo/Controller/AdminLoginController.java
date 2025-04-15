package com.example.demo.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.AdminLogin;
import com.example.demo.Services.AdminLoginService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminLoginController {
	@Autowired
    private AdminLoginService adminService;

	@PostMapping("/login")
	public ResponseEntity<?> loginAdmin(@RequestBody AdminLogin admin) {
	    AdminLogin existingAdmin = adminService.login(admin.getUsername(), admin.getPassword());
	    if (existingAdmin != null) {
	        Map<String, String> response = new HashMap<>();
	        response.put("username",existingAdmin.getUsername());
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    }
	}

}
