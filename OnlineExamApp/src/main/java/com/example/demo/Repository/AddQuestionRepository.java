package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.AddQuestion;

@Repository
public class AddQuestionRepository {
	 @Autowired
	 private JdbcTemplate jdbcTemplate;

	  public void save(AddQuestion question) {
	        String sql = "INSERT INTO questions (paper_set_id, type, question_text, correct_answer, options) VALUES (?, ?, ?, ?, ?)";
	        jdbcTemplate.update(sql,
	            question.getPaperSetId(),
	            question.getType(),
	            question.getQuestionText(),
	            question.getCorrectAnswer(),
	            question.getOptions()
	        );
	    }
}
