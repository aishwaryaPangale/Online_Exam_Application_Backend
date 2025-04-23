package com.example.demo.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.TestResult;
import com.example.demo.Services.TestResultService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins ="http://localhost:5173")
public class TestResultController {

    @Autowired
    private TestResultService testResultService;

    @PostMapping("/submitTest")
    public ResponseEntity<TestResult> submitTest(@RequestBody Map<String, Object> payload) {
        String studentName = (String) payload.get("studentName");
        int testId = Integer.parseInt(payload.get("testId").toString());

        // Convert nested Map (answers)
        Map<String, String> rawAnswers = (Map<String, String>) payload.get("answers");
        Map<Integer, String> answers = new HashMap<>();
        for (Map.Entry<String, String> entry : rawAnswers.entrySet()) {
            answers.put(Integer.parseInt(entry.getKey()), entry.getValue());
        }

        TestResult result = testResultService.generateResult(studentName, testId, answers);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/submit-result")
    public ResponseEntity<String> submitTestResult(
            @RequestParam String username,
            @RequestParam int testId,
            @RequestBody Map<Integer, String> answers) {

        testResultService.submitTestResult(username, testId, answers);
        return ResponseEntity.ok("Result submitted successfully");
    }

//    Report
    @GetMapping("/report/summary")
    public ResponseEntity<Map<String, Object>> getStudentTestSummary(@RequestParam String username) {
        return new ResponseEntity<>(testResultService.getSummaryByUsername(username), HttpStatus.OK);
    }

}
