package com.example.demo.Model;

import lombok.Data;

@Data
public class Test {
	private int id;
	private int batchId;
	private String batchName;
	private int courseId;
	private String courseName;
	private String date;
	private String time;
	private String mode;
	private boolean disable;
	private boolean action;
	private boolean ispaperSet;
}
