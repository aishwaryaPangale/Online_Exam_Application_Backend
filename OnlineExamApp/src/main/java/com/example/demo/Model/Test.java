package com.example.demo.Model;

import lombok.Data;

@Data
public class Test {
	private int id; 
    private String batch;
    private String subject;
    private String date;
    private String time;
    private String mode;
    private boolean disabled;
    private boolean action; 
    private boolean ispaperSet;
}
