package com.example.demo.Services;

import com.example.demo.Model.Test;
import com.example.demo.Repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public void addTest(Test test) {
        testRepository.addTest(test);
    }

    public List<Map<String, Object>> getAllTestsWithJoin() {
        return testRepository.getAllTestsWithJoin();
    }

    public boolean disableTest(int id) {
       return testRepository.disableTest(id);
    }
//    public List<Test> getEnabledTests() {
//        return testRepository.getEnabledTests();
//    }

    public List<Map<String, Object>> searchTests(String keyword) {
       return testRepository.searchTests(keyword);
    }
    
    public void setPaperSet(int testId) {
        testRepository.paperAsSet(testId);
    }
}