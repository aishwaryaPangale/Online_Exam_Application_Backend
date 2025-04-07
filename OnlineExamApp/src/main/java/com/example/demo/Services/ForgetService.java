package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.ForgetRepository;
@Service
public class ForgetService {
	 @Autowired
	    private ForgetRepository forgetRepo;

	    public String updatePassword(String email, String newPassword) {
	        if (!forgetRepo.existsByEmail(email)) {
	            return "❌ Email not registered!";
	        }

	        int result = forgetRepo.updatePasswordByEmail(email, newPassword);
	        if (result > 0) {
	            return "✅ Password updated successfully!";
	        } else {
	            return "❌ Failed to update password.";
	        }
	    }
}
