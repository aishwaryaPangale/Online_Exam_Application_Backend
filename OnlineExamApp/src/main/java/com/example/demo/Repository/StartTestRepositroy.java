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
	        String sql = "SELECT t.*, c.course_name, b.batch_name " +
	                     "FROM test t " +
	                     "JOIN course c ON t.course_id = c.id " +
	                     "JOIN batch b ON t.batch_id = b.id " +
	                     "WHERE t.id = ?";
	        return jdbcTemplate.queryForMap(sql, testId);
	    }

	    public List<Map<String, Object>> getQuestionsByTestId(int testId) {
	        String sql = "SELECT q.* FROM questions q " +
	                     "JOIN test_question tq ON q.id = tq.question_id " +
	                     "WHERE tq.test_id = ?";
	        return jdbcTemplate.queryForList(sql, testId);
	    }


}
