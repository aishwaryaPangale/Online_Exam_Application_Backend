package com.example.demo.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StartTestRepositroy {
	 @Autowired
	    private JdbcTemplate jdbcTemplate;

	    public Map<String, Object> getTestDetails(int testId) {
	        String sql = "SELECT t.id, t.course_id, t.batch_id, t.test_date, t.start_time, t.end_time, " +
	                     "t.total_marks, t.instructions, c.course_name, b.batch_name " +
	                     "FROM test t " +
	                     "JOIN course c ON t.course_id = c.id " +
	                     "JOIN batch b ON t.batch_id = b.id " +
	                     "WHERE t.id = ?";
	        return jdbcTemplate.query(sql, rs -> {
	            if (rs.next()) {
	                Map<String, Object> testDetails = new HashMap<>();
	                testDetails.put("id", rs.getInt("id"));
	                testDetails.put("course_id", rs.getInt("course_id"));
	                testDetails.put("batch_id", rs.getInt("batch_id"));
	                testDetails.put("test_date", rs.getDate("test_date"));
	                testDetails.put("start_time", rs.getTime("start_time"));
	                testDetails.put("end_time", rs.getTime("end_time"));
	                testDetails.put("total_marks", rs.getInt("total_marks"));
	                testDetails.put("instructions", rs.getString("instructions"));
	                testDetails.put("course_name", rs.getString("course_name"));
	                testDetails.put("batch_name", rs.getString("batch_name"));
	                return testDetails;
	            }
	            return null; 
	        }, testId);
	    }

	    public List<Map<String, Object>> getQuestionsByTestId(int testId) {
	        String sql = "SELECT q.id, q.question, q.option1, q.option2, q.option3, q.option4, q.correct_answer " +
	                     "FROM questions q " +
	                     "JOIN test_question tq ON q.id = tq.question_id " +
	                     "WHERE tq.test_id = ?";
	        return jdbcTemplate.query(sql, (rs, rowNum) -> {
	            Map<String, Object> question = new HashMap<>();
	            question.put("id", rs.getInt("id"));
	            question.put("question", rs.getString("question"));
	            question.put("option1", rs.getString("option1"));
	            question.put("option2", rs.getString("option2"));
	            question.put("option3", rs.getString("option3"));
	            question.put("option4", rs.getString("option4"));
	            question.put("correct_answer", rs.getString("correct_answer"));
	            return question;
	        }, testId);
	    }


}
