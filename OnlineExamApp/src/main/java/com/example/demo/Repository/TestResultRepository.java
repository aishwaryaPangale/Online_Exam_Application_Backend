package com.example.demo.Repository;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.TestResult;

@Repository
public class TestResultRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getTotalQuestions(int testId) {
        String sql = "SELECT COUNT(*) FROM test_question WHERE test_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, testId);
    }

    public int getCorrectAnswerCount(int testId, Map<Integer, String> answers) {
        int correct = 0;
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            String sql = "SELECT correct_answer FROM questions WHERE id = ?";
            String correctAnswer = jdbcTemplate.queryForObject(sql, String.class, entry.getKey());
            if (correctAnswer != null && correctAnswer.equalsIgnoreCase(entry.getValue())) {
                correct++;
            }
        }
        return correct;
    }

    public Map<String, Object> getTestMeta(int testId) {
        String sql = "SELECT t.date, t.time, c.course_name, b.batch_name FROM test t JOIN course c ON t.course_id = c.id JOIN batch b ON t.batch_id = b.id WHERE t.id = ?";
        return jdbcTemplate.queryForMap(sql, testId);
    }
    
    public void saveTestResult(String username, int testId, int totalQuestions, int attemptedQuestions, int correctAnswers, int wrongAnswers, int totalMarks) {
      String sql = "INSERT INTO test_result (student_username, test_id, total_questions, attempted_questions, correct_answers, wrong_answer, total_marks) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, username, testId, totalQuestions, attemptedQuestions, correctAnswers, wrongAnswers, totalMarks);
    }

//    Report
    public int getAttendedTestCount(String username) {
        String sql = "SELECT COUNT(*) FROM test_result WHERE Student_username = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }

    public int getNotAttendedTestCount(String username) {
        String sql = "SELECT COUNT(*) FROM test WHERE id NOT IN (SELECT test_id FROM test_result WHERE Student_username= ?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }

    public List<Map<String, Object>> getTestScores(String username) {
        String sql = "SELECT t.date,tr.total_marks  " +
                "FROM test_result tr " +
                "JOIN test t ON tr.test_id = t.id " +
                "WHERE tr.Student_username = ?";
        return jdbcTemplate.queryForList(sql, username);
    }


}

