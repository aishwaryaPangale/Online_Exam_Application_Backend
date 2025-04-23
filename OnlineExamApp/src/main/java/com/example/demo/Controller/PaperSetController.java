package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.AddQuestion;
//import com.example.demo.Model.AddQuestion;
import com.example.demo.Model.PaperSet;
import com.example.demo.Services.PaperSetService;

@RestController
@RequestMapping("/api/paperset")
@CrossOrigin("http://localhost:5173")
public class PaperSetController {
	@Autowired
    private PaperSetService service;
	
//	@Autowired
//	private AddQuestion questionService;
//	
	@PostMapping("/assignQuestions")
    public String assignQuestionsToTest(@RequestBody PaperSet request) {
	   service.assignQuestionsToTest(request.getTestId(), request.getQuestionIds());
       return "✅ Questions assigned to test successfully!";
    }
	
	 @GetMapping("/questions/all")
	 public List<AddQuestion> getAllQuestions() {
	    return service.getAllQuestions();
	 }
//	 @PostMapping("/upload-csv")
//	 public ResponseEntity<?> uploadQuestionsCSV(@RequestParam("file") MultipartFile file,
//	                                             @RequestParam("testId") int testId) {
//	     try {
//	         List<AddQuestion> savedQuestions = questionService.saveQuestionsFromCSV(file);
//	         List<Integer> questionIds = savedQuestions.stream()
//	                                                   .map(AddQuestion::getId)
//	                                                   .collect(Collectors.toList());
//
//	         service.assignQuestionsToTest(testId, questionIds);
//	         return ResponseEntity.ok("Questions uploaded and assigned successfully.");
//	     } catch (Exception e) {
//	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed.");
//	     }
//	 }

}
