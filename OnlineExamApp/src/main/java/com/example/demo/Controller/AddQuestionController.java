package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.AddQuestion;
import com.example.demo.Services.AddQuestionService;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin("http://localhost:5173")
public class AddQuestionController {
	 @Autowired
	    private AddQuestionService service;

	    @PostMapping
	    public void addQuestion(@RequestBody AddQuestion question) {
	        // For MCQ, convert list to JSON string if needed
	        if ("MCQ".equalsIgnoreCase(question.getType()) && question.getOptions() == null) {
	            question.setOptions("[]");
	        }
	        service.addQuestion(question);
	    }
}
