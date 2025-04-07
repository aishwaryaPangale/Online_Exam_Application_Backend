package com.example.demo.Repository;

import com.example.demo.Model.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LoginRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Register mapRow(ResultSet rs, int rowNum) throws SQLException {
        Register r = new Register();
        r.setId(rs.getInt("id"));
        r.setName(rs.getString("name"));
        r.setEmail(rs.getString("email"));
        r.setUsername(rs.getString("username"));
        r.setPassword(rs.getString("password"));
        r.setOtp(rs.getString("otp"));
        return r;
    }

    public List<Register> findByEmail(String email) {
        String sql = "SELECT * FROM students WHERE email = ?";
        return jdbcTemplate.query(sql, this::mapRow, email);
    }

    public List<Register> findByEmailAndUsernameAndPassword(String email, String username, String password) {
        String sql = "SELECT * FROM students WHERE email = ? AND username = ? AND password = ?";
        return jdbcTemplate.query(sql, this::mapRow, email, username, password);
    }
}
