package com.example.demo.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.AddQuestion;
import com.example.demo.Repository.AddQuestionRepository;

@Service
public class AddQuestionService {
	 @Autowired
	    private AddQuestionRepository repo;

	 public void saveQuestionsFromCSV(MultipartFile file) throws IOException {
	        List<AddQuestion> questions = new ArrayList<>();

	        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
	        String line;
	        boolean isFirstLine = true;

	        while ((line = reader.readLine()) != null) {
	            if (isFirstLine) {
	                isFirstLine = false; 
	                continue;
	            }
	            System.out.println("CSV Line: " + line); 
	            String[] data = line.split(",");

	            if (data.length == 6) {
	                AddQuestion q = new AddQuestion();
	                q.setQuestion(data[0]);
	                q.setOptionA(data[1]);
	                q.setOptionB(data[2]);
	                q.setOptionC(data[3]);
	                q.setOptionD(data[4]);
	                q.setCorrectAnswer(data[5]);
	                questions.add(q);
	            }
	        }

	        for (AddQuestion q : questions) {
	        	repo.save(q);
	        }
	    }

	    public List<AddQuestion> getAllQuestions() {
	        return repo.findAll();
	    }
}
