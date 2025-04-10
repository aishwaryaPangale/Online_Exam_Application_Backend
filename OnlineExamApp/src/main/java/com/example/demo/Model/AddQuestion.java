package com.example.demo.Model;

import lombok.Data;

@Data
public class AddQuestion {
	 private int id;
	 private int paperSetId;
	 private String type;
	 private String questionText;
	 private String correctAnswer;
	 private String options; // store as comma-separated or JSON string

}
