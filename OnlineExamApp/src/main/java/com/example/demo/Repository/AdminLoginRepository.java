package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminLoginRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean validateAdmin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM admin WHERE username = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        return count != null && count > 0;
    }
}
