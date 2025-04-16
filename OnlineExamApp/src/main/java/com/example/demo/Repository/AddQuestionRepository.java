package com.example.demo.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.AddQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class AddQuestionRepository {
	@Autowired
    private JdbcTemplate jdbc;

	  public void save(AddQuestion question) {
	        String sql = "INSERT INTO questions (question, option_a, option_b, option_c, option_d, correct_answer) VALUES (?, ?, ?, ?, ?, ?)";
	        jdbc.update(sql, question.getQuestion(), question.getOptionA(), question.getOptionB(), question.getOptionC(), question.getOptionD(), question.getCorrectAnswer());
	    }

    public List<AddQuestion> findAll() {
        return jdbc.query("SELECT * FROM questions", (rs, rowNum) -> {
            AddQuestion q = new AddQuestion();
            q.setId(rs.getInt("id"));
            q.setQuestion(rs.getString("question"));
            q.setOptionA(rs.getString("option_a"));
            q.setOptionB(rs.getString("option_b"));
            q.setOptionC(rs.getString("option_c"));
            q.setOptionD(rs.getString("option_d"));
            q.setCorrectAnswer(rs.getString("correct_answer"));
            return q;
        });
    }
}
