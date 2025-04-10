package com.example.demo.Model;

import java.util.List;

import lombok.Data;

@Data
public class AddQuestion {
	 private int id;
	    private String question;
	    private String optionA;
	    private String optionB;
	    private String optionC;
	    private String optionD;
	    private String correctAnswer;
}
