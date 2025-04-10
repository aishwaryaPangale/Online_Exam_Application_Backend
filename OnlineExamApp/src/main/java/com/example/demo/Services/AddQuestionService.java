package com.example.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.AddQuestion;
import com.example.demo.Repository.AddQuestionRepository;

@Service
public class AddQuestionService {
	 @Autowired
	    private AddQuestionRepository repo;

	    public void addQuestion(AddQuestion question) {
	        repo.save(question);
	    }

	    public List<AddQuestion> getAllQuestions() {
	        return repo.findAll();
	    }
}
