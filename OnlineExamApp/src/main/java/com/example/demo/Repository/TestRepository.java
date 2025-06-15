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
                GROUP_CONCAT(tr.student_id) AS submitted_student_ids
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
                test.setAction(rs.getBoolean("action"));
                test.setDisable(rs.getBoolean("disable"));
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

//    public int updateActionAfterSubmission(int id) {
//        String sql = "UPDATE test SET action = 0 WHERE id = ? and submittedStudentIds = ?";
//        System.out.println("Executing SQL: " + sql + " with ID: " + id); // Add this log
//        return jdbcTemplate.update(sql, id);
//    }
//    
//    public Test findById(int id) {
//        String sql = "SELECT * FROM test WHERE id = ?";
//        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Test.class), id);
//    }
////    disabled
//
    public void disableTest(int id) {
        String sql = "UPDATE test SET disable = 1 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }



//    public int updateActionAndDisableBasedOnSubmission() {
//        String sql = "UPDATE test t SET " +
//                     "action = CASE WHEN EXISTS (SELECT 1 FROM test_result tr WHERE tr.test_id = t.id) THEN 0 ELSE 1 END, " +
//                     "disable = CASE WHEN EXISTS (SELECT 1 FROM test_result tr WHERE tr.test_id = t.id) THEN 1 ELSE 0 END";
//        return jdbcTemplate.update(sql);
//    }

    
    public int updateActionAndDisable(int testId) {
        String sql = """
            UPDATE test t 
            SET 
                action = CASE 
                            WHEN EXISTS (
                                SELECT 1 FROM test_result tr 
                                WHERE tr.test_id = t.id AND tr.test_id = ?
                            ) THEN 0 
                            ELSE 1 
                         END,
                disable = CASE 
                            WHEN EXISTS (
                                SELECT 1 FROM test_result tr 
                                WHERE tr.test_id = t.id AND tr.test_id = ?
                            ) THEN 1 
                            ELSE disable 
                         END
            WHERE t.id = ?
        """;
        return jdbcTemplate.update(sql, testId, testId, testId);
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
