package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.AddQuestion;
import com.example.demo.Repository.AddQuestionRepository;

@Service
public class AddQuestionService {
	@Autowired
    private AddQuestionRepository repository;

    public void addQuestion(AddQuestion question) {
        repository.save(question);
    }
}
