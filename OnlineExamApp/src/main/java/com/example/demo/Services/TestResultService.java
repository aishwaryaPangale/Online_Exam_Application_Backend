package com.example.demo.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Model.TestResult;
import com.example.demo.Repository.TestResultRepository;

@Service
public class TestResultService {

    @Autowired
    private TestResultRepository repository;

    // Generate TestResult object with all computed values but do NOT save
    public TestResult generateResult(int studentId, int testId, Map<Integer, String> answers) {
        int totalQuestions = repository.getTotalQuestions(testId);
        int attempted = answers.size();
        int remaining = totalQuestions - attempted;
        int correct = repository.getCorrectAnswerCount(testId, answers);
        int wrong = attempted - correct;

        TestResult result = new TestResult();
        result.setStudentId(studentId);
        result.setTestId(testId);
        result.setTotalQuestions(totalQuestions);
        result.setAttemptedQuestions(attempted);
        result.setRemainingQuestions(remaining);
        result.setCorrectAnswers(correct);
        result.setWrongAnswers(wrong);
        result.setTotalMarks(correct); // Assuming 1 mark per correct answer

        return result;
    }

    // Save result into DB
    public void submitTestResult(int studentId, int testId, Map<Integer, String> answers) {
        int totalQuestions = repository.getTotalQuestions(testId);
        int correctAnswers = repository.getCorrectAnswerCount(testId, answers);
        int attempted = (int) answers.values().stream()
                         .filter(ans -> ans != null && !ans.trim().isEmpty())
                         .count();
        int wrongAnswers = attempted - correctAnswers;
        int totalMarks = correctAnswers;

        repository.saveTestResult(studentId, testId, totalQuestions, attempted, correctAnswers, wrongAnswers, totalMarks);
    }

    public List<TestResult> getTestResultsByStudent(int studentId) {
        return repository.getStudentTestResults(studentId);
    }

    public List<TestResult> getAllTestResults() {
        return repository.getAllTestResults();
    }
    
    
    public Map<String, Object> getStudentSummary(String username) {
        int attended = repository.countTestsAttended(username);
        int totalTests = repository.countTotalTestsForStudent(username);

        List<Map<String, Object>> scores = repository.getScores(username);

        int notAttended = totalTests - attended;

        Map<String, Object> response = new HashMap<>();
        response.put("attended", attended);
        response.put("notAttended", Math.max(notAttended, 0));
        response.put("scores", scores);
        return response;
    }
}
