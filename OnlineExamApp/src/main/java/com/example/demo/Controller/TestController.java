package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Test;
import com.example.demo.Services.TestService;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = "http://localhost:5173")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/add")
    public ResponseEntity<String> addTest(@RequestBody Test test) {
        testService.addTest(test);
        return ResponseEntity.ok("Test added successfully");
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getAllTests() {
        return testService.getAllTestsWithJoin();
    }	
    @GetMapping("/enabled")
    public List<Test> getEnabledTests() {
        return testService.getEnabledTests();
    }


    @PutMapping("/disable/{id}")
    public ResponseEntity<String> disableTest(@PathVariable int id) {
        testService.disableTest(id);
        return ResponseEntity.ok("Test disabled successfully");
    }
    @GetMapping("/search")
    public List<Test> searchTests(@RequestParam String keyword) {
        return testService.searchTests(keyword);
    }
    
    @PutMapping("/set-paper/{id}")
    public ResponseEntity<Void> paperAsSet(@PathVariable int id) {
        testService.setPaperSet(id);
        return ResponseEntity.ok().build();
    }
}
	