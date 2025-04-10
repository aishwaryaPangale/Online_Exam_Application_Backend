package com.example.demo.Services;

import com.example.demo.Model.Test;
import com.example.demo.Repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public void addTest(Test test) {
        testRepository.addTest(test);
    }

    public List<Test> getAllTests() {
        return testRepository.getAllTests();
    }

    public void disableTest(int id) {
        testRepository.disableTest(id);
    }
    public List<Test> getEnabledTests() {
        return testRepository.getEnabledTests();
    }

    public List<Test> searchTests(String keyword) {
       return testRepository.searchTests(keyword);
    }
}