package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.AddQuestion;
import com.example.demo.Model.PaperSet;
import com.example.demo.Services.PaperSetService;

@RestController
@RequestMapping("/api/paperset")
@CrossOrigin("http://localhost:5173")
public class PaperSetController {
	@Autowired
    private PaperSetService service;
	
	@PostMapping("/assignQuestions")
    public String assignQuestionsToTest(@RequestBody PaperSet request) {
	   service.assignQuestionsToTest(request.getTestId(), request.getQuestionIds());
       return "✅ Questions assigned to test successfully!";
    }
	
	 @GetMapping("/questions/all")
	 public List<AddQuestion> getAllQuestions() {
	    return service.getAllQuestions();
	 }
}
