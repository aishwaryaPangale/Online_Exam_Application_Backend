package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Register;

import java.util.List;

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
        String sql = "INSERT INTO students (name, email, contact, address, course, birthdate, gender, username, password,batch) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        int rowsAffected = jdbcTemplate.update(sql,
                student.getName(), student.getEmail(), student.getContact(),
                student.getAddress(), student.getCourse(), student.getBirthdate(),
                student.getGender(), student.getUsername(), student.getPassword(),student.getBatch());

        return rowsAffected > 0; // ✅ This line was missing
    }

    public boolean validateLogin(String email, String username, String password) {
        String sql = "SELECT * FROM students WHERE email = ? AND username = ? AND password = ?";
        List<Register> students = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Register.class), email, username, password);
        return students.size() == 1;
    }
    
    
    
    /*curd operation*/


    public List<Register> getAllStudents() {
        String sql = "SELECT * FROM students";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Register.class));
    }

    
    public int deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Register getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Register.class), id);
    }

    public int updateStudent(Register student) {
        String sql = "UPDATE students SET name=?, email=?, contact=?, birthdate=?, gender=?, address=?, course=?, batch=?, username=?, password=? " +
                     "WHERE id=?";
        return jdbcTemplate.update(sql, student.getName(), student.getEmail(), student.getContact(),
                student.getBirthdate(), student.getGender(), student.getAddress(),
                student.getCourse(), student.getBatch(), student.getUsername(), student.getPassword(),
                student.getId());
    }
    
    public Register getStudentByUsername(String username) {
        String sql = "SELECT * FROM students WHERE username = ?";
        List<Register> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Register.class), username);
        return list.isEmpty() ? null : list.get(0);
    }


}

