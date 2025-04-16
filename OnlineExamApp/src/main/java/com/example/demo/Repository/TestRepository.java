package com.example.demo.Repository;

import com.example.demo.Model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class TestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addTest(Test test) {
        String sql = "INSERT INTO test (batch_id, course_id, date, time, mode) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, test.getBatchId(), test.getCourseId(), test.getDate(), test.getTime(), test.getMode());
    }

    public List<Map<String, Object>> getAllTestsWithJoin() {
        String sql = "SELECT t.id, b.batch_name, c.course_name, t.date, t.time, t.mode " +
                     "FROM test t " +
                     "JOIN batch b ON t.batch_id = b.id " +
                     "JOIN course c ON t.course_id = c.id";
        return jdbcTemplate.queryForList(sql);
    }

    public void disableTest(int id) {
        String sql = "UPDATE test SET disabled = true WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    public List<Test> getEnabledTests() {
        String sql = "SELECT * FROM test WHERE disabled = false";
        return jdbcTemplate.query(sql, new TestRowMapper());
    }

    public List<Test> searchTests(String keyword) {
        String sql = "SELECT * FROM tests WHERE LOWER(batch) LIKE ? OR LOWER(subject) LIKE ?";
        String likePattern = "%" + keyword.toLowerCase() + "%";
        return jdbcTemplate.query(sql, new Object[]{likePattern, likePattern}, new TestRowMapper());
    }

    private static class TestRowMapper implements RowMapper<Test> {
        @Override
        public Test mapRow(ResultSet rs, int rowNum) throws SQLException {
            Test test = new Test();
            test.setId(rs.getInt("id"));
            test.setDate(rs.getString("date")); 
            test.setTime(rs.getString("time"));
            test.setMode(rs.getString("mode"));
            test.setDisabled(rs.getBoolean("disabled"));
            test.setAction(rs.getBoolean("action"));
            test.setIspaperSet(rs.getBoolean("ispaperSet"));
            return test;
        }
    }
    
    public void paperAsSet(int testId) {
        String sql = "UPDATE test SET isPaperSet = 1 WHERE id = ?";
        jdbcTemplate.update(sql, testId);
    }
}

