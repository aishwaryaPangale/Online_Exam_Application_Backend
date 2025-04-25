package com.example.demo.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.StartTestService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin("http://localhost:5173")
public class StartTestController {

	@Autowired
	private StartTestService startService;
	@GetMapping("/startTest")
    public Map<String, Object> startTest(@RequestParam int testId) {
        return startService.getTestDetailsWithQuestions(testId);
    }
}
