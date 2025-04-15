package com.example.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.AddQuestion;
import com.example.demo.Model.PaperSet;
import com.example.demo.Repository.PaperSetRepository;

@Service
public class PaperSetService {
	@Autowired
    private PaperSetRepository repository;

//	public void assignQuestionsToTest(int testId, List<Integer> questionIds) {
//        for (int questionId : questionIds) {
//        	repository.insertTestQuestionMapping(testId, questionId);
//        }
//    }
	
	public void assignQuestionsToTest(int testId, List<Integer> questionIds) {
      for (int questionId : questionIds) {
      	repository.insertTestQuestionMapping(testId, questionId);
      }
      
      repository.updateIsPaperSetFlag(testId);

  }
	
	public List<AddQuestion> getAllQuestions() {
        return repository.findAll();
    }
}
