package com.example.demo.Repository;

import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.AdminLogin;

@Repository
public class AdminLoginRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;

    public AdminLogin validateAdmin(String username, String password) {
        String sql = "SELECT username,password FROM admin_table WHERE username = ? AND password = ?";
        List<AdminLogin> admins = jdbcTemplate.query(sql, new Object[]{username, password}, new RowMapper<AdminLogin>() {
            @Override
            public AdminLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
            	AdminLogin admin = new AdminLogin();
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                return admin;
            }
        });

        return admins.isEmpty() ? null : admins.get(0);
    }
}
