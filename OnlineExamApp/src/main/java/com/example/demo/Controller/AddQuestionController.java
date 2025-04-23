package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.AddQuestion;
import com.example.demo.Services.AddQuestionService;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins ="http://localhost:5173")
public class AddQuestionController {
	 @Autowired
	    private AddQuestionService service;

	 @PostMapping("/upload-csv")
	    public ResponseEntity<String> uploadQuestionsCSV(@RequestParam("file") MultipartFile file) {
	        try {
	        	service.saveQuestionsFromCSV(file);
	            return ResponseEntity.ok("Questions uploaded successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed.");
	        }
	    }
	    @GetMapping("/all")
	    public List<AddQuestion> getAllQuestions() {
	        return service.getAllQuestions();
	    }
}
