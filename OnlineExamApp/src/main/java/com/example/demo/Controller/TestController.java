package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Test;
import com.example.demo.Services.TestService;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = "*")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/add")
    public ResponseEntity<String> addTest(@RequestBody Test test) {
        testService.addTest(test);
        return ResponseEntity.ok("Test added successfully");
    }

    @GetMapping("/all")
    public List<Test> getAllTests() {
        return testService.getAllTests();
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
}
	