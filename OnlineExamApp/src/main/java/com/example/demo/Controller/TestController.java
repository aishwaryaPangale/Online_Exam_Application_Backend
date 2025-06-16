package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public String addTest(@RequestBody Test test) {
        testService.addTest(test);
        return "Test added successfully";
    }

    @GetMapping("/all")
    public List<Test> getAllTests() {
    	
    	List<Test> list=testService.getAllTestsWithJoin();
        return list;
    }

//action 
    @PutMapping("/disable/{id}")
    public ResponseEntity<String> disableTest(@PathVariable int id) {
        System.out.println("Received request to disable test with ID: " + id);
        int rows = testService.disableTest(id);
        return rows > 0 ? ResponseEntity.ok("Test disabled.") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test not found.");
    }

    @PutMapping("/submit/{id}")
    public ResponseEntity<String> updateTestAction(@PathVariable int id) {
        System.out.println("Received request to update action for test with ID: " + id);
        int rows = testService.updateAction(id);
        return rows > 0 ? ResponseEntity.ok("Test action updated.") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test not found.");
    }
//search test
    @GetMapping("/search")
    public List<Test> searchTests(@RequestParam String keyword) {
        return testService.searchTests(keyword);
    }

//    set paper
    @PutMapping("/set-paper/{id}")
    public String paperAsSet(@PathVariable int id) {
        testService.setPaperSet(id);
        return "Paper set successfully.";
    }
//test available or not
    @GetMapping("/available")
    public List<Test> getAvailableTestsByUsername(@RequestParam String username) {
    	System.out.println(username);
        return testService.getAvailableTestsByUsername(username);
    }


}
	