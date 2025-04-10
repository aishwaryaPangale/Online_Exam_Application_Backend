package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

	 @PostMapping("/add")
	    public ResponseEntity<String> addQuestion(@RequestBody AddQuestion question) {
	        service.addQuestion(question);
	        return ResponseEntity.ok("Question added");
	    }

	    @GetMapping("/all")
	    public List<AddQuestion> getAllQuestions() {
	        return service.getAllQuestions();
	    }
}
