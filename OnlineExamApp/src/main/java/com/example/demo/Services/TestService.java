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
    public boolean disableTestById(int testId) {
        return testRepository.disableTestIfSubmitted(testId);
    }

    public int updateActionAndDisable(int id) {
        return testRepository.updateActionAndDisable(id);
    }

    
//    search test

    public List<Test> searchTests(String keyword) {
       return testRepository.searchTests(keyword);
    }
//    set paper
    
    public void setPaperSet(int testId) {
        testRepository.paperAsSet(testId);
    }
    
//    test available or not
    public List<Test> getAvailableTestsByUsername(String username) {
        return testRepository.findAvailableTestsByUsername(username);
       
    }
}