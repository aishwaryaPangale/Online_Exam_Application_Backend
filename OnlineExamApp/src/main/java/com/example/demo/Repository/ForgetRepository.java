package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository	
public class ForgetRepository {
	 @Autowired
	    private JdbcTemplate jdbcTemplate;

	    public int updatePasswordByEmail(String email, String newPassword) {
	        String sql = "UPDATE students SET password = ? WHERE email = ?";
	        return jdbcTemplate.update(sql, newPassword, email);
	    }

	    public boolean existsByEmail(String email) {
	        String sql = "SELECT COUNT(*) FROM students WHERE email = ?";
	        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
	        return count != null && count > 0;
	    }
}
