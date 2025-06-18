package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.TestResult;
import com.example.demo.Repository.TestRepository;
import com.example.demo.Services.TestResultService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:5173")
public class TestResultController {

    @Autowired
    private TestResultService testResultService;
    @Autowired
    private TestRepository testRepository;		

    // Submit test with full result generation
    @PostMapping("/submitTest")
    public ResponseEntity<TestResult> submitTest(@RequestBody Map<String, Object> payload) {
        try {
            // Extract studentId and testId
            Object studentIdObj = payload.get("studentId");
            Object testIdObj = payload.get("testId");
            if (studentIdObj == null || testIdObj == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            int studentId = ((Number) studentIdObj).intValue();
            int testId = ((Number) testIdObj).intValue();

            // Extract answers map (keys are question IDs as strings)
            Map<String, String> rawAnswers = (Map<String, String>) payload.get("answers");
            if (rawAnswers == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Convert keys to Integer question IDs
            Map<Integer, String> answers = new HashMap<>();
            for (Map.Entry<String, String> entry : rawAnswers.entrySet()) {
                answers.put(Integer.parseInt(entry.getKey()), entry.getValue());
            }

            TestResult result = testResultService.generateResult(studentId, testId, answers);

            // Optionally save result here, or separately via /submit-result endpoint
            testResultService.submitTestResult(studentId, testId, answers);
            testRepository.updateSubmittedStudentIds(testId);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Alternative endpoint: just saving result from answers map, called separately
    @PostMapping("/submit-result")
    public ResponseEntity<String> submitTestResult(
            @RequestParam int studentId,
            @RequestParam int testId,
            @RequestBody Map<Integer, String> answers) {
        try {
            testResultService.submitTestResult(studentId, testId, answers);
            return ResponseEntity.ok("Result submitted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to submit result");
        }
    }

    @GetMapping("/report/{studentId}")
    public List<TestResult> getStudentTestResults(@PathVariable int studentId) {
        return testResultService.getTestResultsByStudent(studentId);
    }

    @GetMapping("/all")
    public List<TestResult> getAllTestResults() {
        return testResultService.getAllTestResults();
    }
    
    @GetMapping("/report/summary")
    public Map<String, Object> getStudentReportSummary(@RequestParam String username) {
        return testResultService.getStudentSummary(username);
    }
}
