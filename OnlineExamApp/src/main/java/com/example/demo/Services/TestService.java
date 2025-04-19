package com.example.demo.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Test;
import com.example.demo.Repository.TestRepository;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public void addTest(Test test) {
        testRepository.addTest(test);
    }

    public List<Test> getAllTestsWithJoin() {
        return testRepository.getAllTestsWithJoin();
    }

    public boolean disableTest(int id) {
       return testRepository.disableTest(id);
    }
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