package com.example.demo.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.StartTestRepositroy;

@Service
public class StartTestService {

	@Autowired
	StartTestRepositroy startRepo;
	private Map<String, Object> toLowerCaseKeys(Map<String, Object> input) {
	    Map<String, Object> result = new HashMap<>();
	    for (Map.Entry<String, Object> entry : input.entrySet()) {
	        result.put(entry.getKey().toLowerCase(), entry.getValue());
	    }
	    return result;
	}

	public Map<String, Object> getTestDetailsWithQuestions(int testId) {
	    Map<String, Object> result = new HashMap<>();

	    Map<String, Object> testDetails = startRepo.getTestDetails(testId);
	    List<Map<String, Object>> questions = startRepo.getQuestionsByTestId(testId);

	    result.put("test", toLowerCaseKeys(testDetails));

	    // For questions, lowercase keys too (optional, recommended)
	    List<Map<String, Object>> lowerQuestions = questions.stream()
	        .map(this::toLowerCaseKeys)
	        .toList();
	    result.put("questions", lowerQuestions);

	    return result;
	}

	
	
}
