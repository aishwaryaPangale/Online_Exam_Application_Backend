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
//    public boolean updateActionIfResultExists(int id) {
//        boolean hasResult = testResultRepository.doesTestHaveResult(id);
//        if (hasResult) {
//            testRepository.setTestActionToFalse(id);
//            return true;
//        }
//        return false;
//    }
    
    public boolean updateActionIfResultExists(int id) {
        int rowsUpdated = testRepository.updateActionIfResultExists(id);
        return rowsUpdated > 0;
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