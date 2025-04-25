package com.example.demo.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Test;
import com.example.demo.Repository.TestRepository;
import com.example.demo.Repository.TestResultRepository;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;
    
    @Autowired 
    private TestResultRepository testResultRepository;

    public void addTest(Test test) {
        testRepository.addTest(test);
    }

    public List<Test> getAllTestsWithJoin() {
        return testRepository.getAllTestsWithJoin();
    }
//Action
    
    public boolean markTestAsSubmitted(int id) {
        System.out.println("Marking test " + id + " as submitted."); // Add this log
        int rowsUpdated = testRepository.updateActionAfterSubmission(id);
        System.out.println("Number of rows updated: " + rowsUpdated + " for test ID: " + id); // Add this log
        return rowsUpdated > 0;
    }
    
    public Test getTestById(int id) {
        return testRepository.findById(id); // Implement this
    }

//disable
    public void disableTest(int id) {
        testRepository.disableTest(id);
    }

//    public boolean disableTest(int id) {
//       return testRepository.disableTest(id);
//    }
//    public List<Test> getEnabledTests() {
//        return testRepository.getEnabledTests();
//    }

    public List<Test> searchTests(String keyword) {
       return testRepository.searchTests(keyword);
    }
    
    public void setPaperSet(int testId) {
        testRepository.paperAsSet(testId);
    }
    
    public List<Test> getAvailableTestsByUsername(String username) {
        return testRepository.findAvailableTestsByUsername(username);
       
    }
}