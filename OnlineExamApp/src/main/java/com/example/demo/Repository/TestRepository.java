package com.example.demo.Repository;

import com.example.demo.Model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
        String sql = """
            SELECT 
                t.id,
                b.batch_name,
                c.course_name,
                t.date,
                t.time,
                t.mode,
                t.disable,
                t.action,
                t.isPaperset,
               GROUP_CONCAT(DISTINCT tr.student_id) AS submitted_student_ids
            FROM test t
            JOIN batch b ON t.batch_id = b.id
            JOIN course c ON t.course_id = c.id
            LEFT JOIN test_result tr ON tr.test_id = t.id
            GROUP BY t.id, b.batch_name, c.course_name, t.date, t.time, t.mode, t.disable, t.action, t.isPaperset
        """;

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
                test.setAction(rs.getInt("action"));
                test.setDisable(rs.getInt("disable"));
                test.setIspaperSet(rs.getBoolean("isPaperset"));
                test.setSubmittedStudentIds(rs.getString("submitted_student_ids") != null ? rs.getString("submitted_student_ids") : "");
                return test;
            }
        });
    }
    
    
    public void updateSubmittedStudentIds(int testId) {
        String sql = """
            UPDATE test
            SET submitted_student_ids = (
                SELECT GROUP_CONCAT(student_id)
                FROM test_result
                WHERE test_id = ?
            )
            WHERE id = ?
        """;
        jdbcTemplate.update(sql, testId, testId);
    }


//Action Logic
 // Disable the test ONLY IF students have submitted it
    public boolean disableTestIfSubmitted(int testId) {
        String checkSql = "SELECT COUNT(*) FROM test_result WHERE test_id = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, testId);

        if (count != null && count > 0) {
            String updateSql = "UPDATE test SET disable = true, action = false WHERE id = ?";
            jdbcTemplate.update(updateSql, testId);
            return true;
        }
        return false; // No submission, don't disable
    }


    public int updateActionAndDisable(int id) {
        String sql = "UPDATE test SET action = false WHERE id = ?";
        return jdbcTemplate.update(sql, id);
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
    	String sql = """
    		   SELECT t.id, t.date, b.batch_name, c.course_name, t.time
    FROM test t
    JOIN batch b ON b.id = t.batch_id
    JOIN students s ON s.batch = b.batch_name
    JOIN course c ON c.id = t.course_id
    WHERE s.username = ? AND t.ispaperSet = 1
    		""";
	
     

    	 return jdbcTemplate.query(sql, new Object[]{username}, new RowMapper<Test>() {
    	        @Override
    	        public Test mapRow(ResultSet rs, int rowNum) throws SQLException {
    	            Test test = new Test();
    	            test.setId(rs.getInt("id"));
    	            test.setDate(rs.getString("date"));
    	            test.setBatchName(rs.getString("batch_name"));
    	            test.setCourseName(rs.getString("course_name"));
    	            test.setTime(rs.getString("time"));
    	            System.out.println(test);
    	            return test;
    	           
    	        }
    	    });
    }
}
