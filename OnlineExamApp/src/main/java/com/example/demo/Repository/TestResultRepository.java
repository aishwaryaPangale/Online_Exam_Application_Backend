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

    // Count total questions for a test
    public int getTotalQuestions(int testId) {
        String sql = "SELECT COUNT(*) FROM test_question WHERE test_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, testId);
    }

    // Count how many answers are correct for this test based on answers map
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

    // Save test result to test_result table
    public void saveTestResult(int studentId, int testId, int totalQuestions, int attemptedQuestions, int correctAnswers, int wrongAnswers, int totalMarks) {
        String sql = "INSERT INTO test_result (student_id, test_id, total_questions, attempted_questions, correct_answers, wrong_answers, total_marks) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, studentId, testId, totalQuestions, attemptedQuestions, correctAnswers, wrongAnswers, totalMarks);
    }

    // Retrieve all test results of a student
    public List<TestResult> getStudentTestResults(int studentId) {
        String sql = "SELECT * FROM test_result WHERE student_id = ?";
        return jdbcTemplate.query(sql, new Object[]{studentId}, (rs, rowNum) -> {
            TestResult result = new TestResult();
            result.setStudentId(rs.getInt("student_id"));
            result.setTestId(rs.getInt("test_id"));
            result.setTotalQuestions(rs.getInt("total_questions"));
            result.setAttemptedQuestions(rs.getInt("attempted_questions"));
            result.setCorrectAnswers(rs.getInt("correct_answers"));
            result.setWrongAnswers(rs.getInt("wrong_answers"));
            result.setTotalMarks(rs.getInt("total_marks"));
            result.setRemainingQuestions(result.getTotalQuestions() - result.getAttemptedQuestions());
            return result;
        });
    }

    // Retrieve all test results
    public List<TestResult> getAllTestResults() {
        String sql = """
            SELECT tr.test_id, tr.total_questions, tr.attempted_questions,
                   tr.correct_answers, tr.wrong_answers, tr.total_marks,
                   s.name AS student_name
            FROM test_result tr
            JOIN students s ON tr.student_id = s.id
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TestResult result = new TestResult();
            result.setTestId(rs.getInt("test_id"));
            result.setTotalQuestions(rs.getInt("total_questions"));
            result.setAttemptedQuestions(rs.getInt("attempted_questions"));
            result.setCorrectAnswers(rs.getInt("correct_answers"));
            result.setWrongAnswers(rs.getInt("wrong_answers"));
            result.setTotalMarks(rs.getInt("total_marks"));
            result.setStudentName(rs.getString("student_name")); // Important!
            return result;
        });
    }

    
    public int countTestsAttended(String username) {
        String sql = "SELECT COUNT(*) FROM test_result WHERE student_id = " +
                     "(SELECT id FROM students WHERE username = ?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }

    // Total tests available for the student's batch
    public int countTotalTestsForStudent(String username) {
        String sql = """
            SELECT COUNT(*) 
            FROM test t
            JOIN students s ON s.batch = (SELECT batch FROM students WHERE username = ?)
            WHERE t.batch_id = (SELECT id FROM batch WHERE batch_name = s.batch)
              AND t.isPaperSet = 1
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }

    // Test scores with testId and total marks
    public List<Map<String, Object>> getScores(String username) {
        String sql = """
            SELECT tr.test_id, tr.total_marks
            FROM test_result tr
            JOIN students s ON tr.student_id = s.id
            WHERE s.username = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{username}, (rs, rowNum) -> {
            Map<String, Object> score = new HashMap<>();
            score.put("testId", rs.getInt("test_id"));
            score.put("total_marks", rs.getInt("total_marks"));
            return score;
        });
    }
}
