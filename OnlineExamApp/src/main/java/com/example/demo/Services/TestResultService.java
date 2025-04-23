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

    public TestResult generateResult(String studentName, int testId, Map<Integer, String> answers) {
        int totalQuestions = repository.getTotalQuestions(testId);
        int attempted = answers.size();
        int remaining = totalQuestions - attempted;
        int correct = repository.getCorrectAnswerCount(testId, answers);
        int wrong = attempted - correct;

        TestResult result = new TestResult();
        result.setStudentName(studentName);
        result.setTestId(testId);
        result.setTotalQuestions(totalQuestions);
        result.setAttemptedQuestions(attempted);
        result.setRemainingQuestions(remaining);
        result.setCorrectAnswers(correct);
        result.setWrongAnswers(wrong);
        result.setTotalMarks(correct); // 1 mark per question

        return result;
    }
    
    public void submitTestResult(String username, int testId, Map<Integer, String> answers) {
        int totalQuestions = repository.getTotalQuestions(testId);
        int correctAnswers = repository.getCorrectAnswerCount(testId, answers);
        int attempted = (int) answers.values().stream().filter(ans -> ans != null && !ans.trim().isEmpty()).count();
        int totalMarks = correctAnswers;

        repository.saveTestResult(username, testId, totalQuestions, totalQuestions, correctAnswers, attempted, totalMarks);;
    }
//    Report
    public Map<String, Object> getSummaryByUsername(String username) {
        int attended = repository.getAttendedTestCount(username);
        int notAttended = repository.getNotAttendedTestCount(username);
        List<Map<String, Object>> scores = repository.getTestScores(username);

        Map<String, Object> result = new HashMap<>();
        result.put("attended", attended);
        result.put("notAttended", notAttended);
        result.put("scores", scores); // each map contains testName and score
        return result;
    }

}
