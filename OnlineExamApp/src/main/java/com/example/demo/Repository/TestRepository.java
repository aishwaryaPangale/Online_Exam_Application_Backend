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
//Action Logic

    
//    public void updateTestActionFlag(int id) {
//        String sql = "UPDATE test SET action = false WHERE id = ?";
//        jdbcTemplate.update(sql, id);
//    }
//
//    public List<Test> findTestsByTestId(int id) {
//        String sql = "SELECT * FROM test WHERE id = ?";
//        return jdbcTemplate.query(sql, new Object[]{id}, new TestRowMapper());
//    }
//    public class TestRowMapper implements RowMapper<Test> {
//        @Override
//        public Test mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Test test = new Test();
//            test.setId(rs.getInt("id"));
//            test.setBatchName(rs.getString("batchName"));
//            test.setCourseName(rs.getString("courseName"));
//            test.setDate(rs.getString("date")); // If using java.sql.Date, use rs.getDate
//            test.setTime(rs.getString("time")); // If using java.sql.Time, use rs.getTime
//            test.setMode(rs.getString("mode"));
//            test.setAction(rs.getBoolean("action")); // If you have an 'action' column
//            return test;
//        }
//    }

//    public void setTestActionToFalse(int id) {
//        String sql = "UPDATE test SET action = 0 WHERE id = ?";
//    	String sql = "UPDATE test SET action = 0 WHERE id = ? AND action != 0";
//
//       int rows= jdbcTemplate.update(sql, id);
//        System.out.println("Rows updated: " + rows); // <--- Debug log
//
//    }
    
    public int updateActionAfterSubmission(int id) {
        String sql = "UPDATE test SET action = 0 WHERE id = ?";
        System.out.println("Executing SQL: " + sql + " with ID: " + id); // Add this log
        return jdbcTemplate.update(sql, id);
    }
    
    public Test findById(int id) {
        String sql = "SELECT * FROM test WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Test.class), id);
    }

    public void disableTest(int id) {
        String sql = "UPDATE test SET disable = true WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    // Disable a test by ID
//    public boolean disableTest(int id) {
//        String sql = "UPDATE test SET disable = true WHERE id = ?";
//        int rows = jdbcTemplate.update(sql, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setInt(1, id);
//            }
//        });
//        return rows > 0;
//    }


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
