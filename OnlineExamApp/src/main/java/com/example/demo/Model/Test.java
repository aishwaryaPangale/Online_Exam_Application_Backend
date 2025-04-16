package com.example.demo.Model;

import lombok.Data;

@Data
public class Test {
	private int id;
	private int batchId;
	private int courseId;
	private String date;
	private String time;
	private String mode;
	private boolean disabled;
	private boolean action;
	private boolean ispaperSet;
}
