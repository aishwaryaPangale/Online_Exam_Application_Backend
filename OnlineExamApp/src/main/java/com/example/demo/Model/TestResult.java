package com.example.demo.Model;

import lombok.Data;

@Data
public class TestResult {

	 private String studentName;
	    private int testId;
	    private int totalQuestions;
	    private int attemptedQuestions;
	    private int remainingQuestions;
	    private int correctAnswers;
	    private int wrongAnswers;
	    private int totalMarks;
}
