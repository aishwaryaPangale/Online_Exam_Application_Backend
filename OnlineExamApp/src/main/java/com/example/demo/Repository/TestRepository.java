package com.example.demo.Repository;

import com.example.demo.Model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add a new test
    public void addTest(Test test) {
        String sql = "INSERT INTO test (batch_id, course_id, date, time, mode) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, test.getBatchId(), test.getCourseId(), test.getDate(), test.getTime(), test.getMode());
    }

    // Get all tests with join on batch and course
    public List<Map<String, Object>> getAllTestsWithJoin() {
        String sql = "SELECT t.id, b.batch_name, c.course_name, t.date, t.time, t.mode, t.action, t.disable, t.ispaperSet "
                   + "FROM test t "
                   + "JOIN batch b ON t.batch_id = b.id "
                   + "JOIN course c ON t.course_id = c.id";
        return jdbcTemplate.queryForList(sql);
    }

    // Disable a test by ID
    public boolean disableTest(int id) {
        String sql = "UPDATE test SET disable = true WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    // Search tests by keyword (batch or course name)
    public List<Map<String, Object>> searchTests(String keyword) {
        String sql = "SELECT t.id, b.batch_name, c.course_name, t.date, t.time, t.mode "
                   + "FROM test t "
                   + "JOIN batch b ON t.batch_id = b.id "
                   + "JOIN course c ON t.course_id = c.id "
                   + "WHERE LOWER(b.batch_name) LIKE ? OR LOWER(c.course_name) LIKE ?";
        String likePattern = "%" + keyword.toLowerCase() + "%";
        return jdbcTemplate.queryForList(sql, likePattern, likePattern);
    }

    // Set paper flag as true for a test
    public void paperAsSet(int testId) {
        String sql = "UPDATE test SET isPaperSet = 1 WHERE id = ?";
        jdbcTemplate.update(sql, testId);
    }
}
