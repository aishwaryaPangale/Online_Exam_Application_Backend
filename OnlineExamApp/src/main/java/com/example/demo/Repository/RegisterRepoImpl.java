package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Register;

@Repository
public class RegisterRepoImpl implements RegisterRepo{
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
    public boolean isEmailRegistered(String email, String role) {
        String table = role.equalsIgnoreCase("student") ? "students" : "admins";
        String sql = "SELECT COUNT(*) FROM " + table + " WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

	public void saveUser(Register reg) {
        String sql;
        if ("student".equalsIgnoreCase(reg.getRole())) {
            sql = "INSERT INTO students (name, email, contact, address, course, birthdate, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO admins (name, email, contact, address, course, birthdate, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        }

        jdbcTemplate.update(sql,reg.getName(),reg.getEmail(),reg.getContact(), reg.getAddress(), reg.getCourse(),
                reg.getBirthdate(),reg.getGender()
        );
    }
}
