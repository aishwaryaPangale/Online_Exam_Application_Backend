package com.example.demo.Repository;

import com.example.demo.Model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add a new test
    public void addTest(Test test) {
    	PreparedStatementSetter pstmt=new PreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps) throws SQLException {
				 ps.setInt(1, test.getBatchId());
		            ps.setInt(2, test.getCourseId());
		            ps.setString(3, test.getDate());
		            ps.setString(4, test.getTime());
		            ps.setString(5, test.getMode());
				
			}
		};
		jdbcTemplate.update("insert into test (batch_id, course_id, date, time, mode) values (?, ?, ?, ?, ?)", pstmt);
       
    }
	

    // Get all tests with join on batch and course
    public List<Test> getAllTestsWithJoin() {
        String sql = "select t.id, b.batch_name, c.course_name, t.date, t.time, t.mode, t.action, t.disable, t.ispaperSet " +
                     "from test t join batch b ON t.batch_id = b.id join course c ON t.course_id = c.id";

        return jdbcTemplate.query(sql, new RowMapper<Test>() {
            @Override
            public Test mapRow(ResultSet rs, int rowNum) throws SQLException {
                Test test = new Test();
                test.setId(rs.getInt("id"));
                test.setBatchName(rs.getString("batch_name"));
                test.setCourseName(rs.getString("course_name"));
                test.setDate(rs.getString("date"));
                test.setTime(rs.getString("time"));
                test.setMode(rs.getString("mode"));
                test.setAction(rs.getBoolean("action"));
                test.setDisable(rs.getBoolean("disable"));
                test.setIspaperSet(rs.getBoolean("ispaperSet"));
                return test;
            }
        });
    }


    // Disable a test by ID
    public boolean disableTest(int id) {
        String sql = "UPDATE test SET disable = true WHERE id = ?";
        int rows = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }
        });
        return rows > 0;
    }


    // Search tests by keyword (batch or course name)
    public List<Test> searchTests(String keyword) {
        String sql = "select t.id, b.batch_name, c.course_name, t.date, t.time, t.mode from test t join batch b ON t.batch_id = b.id join course c ON t.course_id = c.id WHERE LOWER(b.batch_name) LIKE ? OR LOWER(c.course_name) LIKE ?";

        String likePattern = "%" + keyword.toLowerCase() + "%";

        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, likePattern);
                ps.setString(2, likePattern);
            }
        }, new RowMapper<Test>() {
            @Override
            public Test mapRow(ResultSet rs, int rowNum) throws SQLException {
                Test test = new Test();
                test.setId(rs.getInt("id"));
                test.setBatchName(rs.getString("batch_name"));
                test.setCourseName(rs.getString("course_name"));
                test.setDate(rs.getString("date"));
                test.setTime(rs.getString("time"));
                test.setMode(rs.getString("mode"));
                return test;
            }
        });
    }


    // Set paper flag as true for a test
    public boolean paperAsSet(int testId) {
        String sql = "UPDATE test SET isPaperSet = 1 WHERE id = ?";
        int rows = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, testId);
            }
        });
        return rows > 0;
    }

    
    //Available Test
    public List<Test> findAvailableTestsByUsername(String username) {
        String sql = "select t.id, t.date, t.batch_name, t.course_name, t.time, t.mode, t.disable, t.action, t.ispaperSet " +
                     "from test t join students s ON t.batch_id = s.batch where s.username = ? AND t.ispaperSet = true";

        return jdbcTemplate.query(sql, ps -> ps.setString(1, username), (rs, rowNum) -> {
            Test test = new Test();
            test.setId(rs.getInt("id"));
            test.setDate(rs.getString("date"));
            test.setBatchId(rs.getInt("batch_name"));
            test.setCourseId(rs.getInt("course_name"));
            test.setTime(rs.getString("time"));
            test.setMode(rs.getString("mode"));
            test.setDisable(rs.getBoolean("disable"));
            test.setAction(rs.getBoolean("action"));
            test.setIspaperSet(rs.getBoolean("ispaperSet"));
            return test;
        });
    }

}
