package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Register;

@Repository
public class RegisterRepoImpl {
	    @Autowired
	    private JdbcTemplate jdbcTemplate;

	   public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM students WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public boolean saveStudent(Register student) {
        String sql = "INSERT INTO students (name, email, contact, address, course, birthdate, gender, username, password) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                student.getName(), student.getEmail(), student.getContact(),
                student.getAddress(), student.getCourse(), student.getBirthdate(),
                student.getGender(), student.getUsername(), student.getPassword());
		return false;
    }

    public boolean validateLogin(String email, String username, String password) {
    	String sql = "SELECT * FROM students WHERE email = ? AND username = ? AND password = ?";
    	List<Register> students = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Register.class), email, username, password);

    	if (students.size() == 1) {
    	    return true; // login success
    	} else {
    	    return false; // invalid credentials
    	}

    }

		
    }
    
