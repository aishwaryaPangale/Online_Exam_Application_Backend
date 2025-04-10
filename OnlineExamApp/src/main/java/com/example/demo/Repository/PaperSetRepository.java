package com.example.demo.Repository;

import com.example.demo.Model.AddQuestion;
import com.example.demo.Model.PaperSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class PaperSetRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertTestQuestionMapping(int testId, int questionId) {
        String sql = "INSERT INTO test_question (test_id, question_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, testId, questionId);
    }
    
    public List<AddQuestion> findAll() {
        String sql = "SELECT id, question FROM question";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AddQuestion q = new AddQuestion();
            q.setId(rs.getInt("id"));
            q.setQuestion(rs.getString("question"));
            return q;
        });
    }
 }
