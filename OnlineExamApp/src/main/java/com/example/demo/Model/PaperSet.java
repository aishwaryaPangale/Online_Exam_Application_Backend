package com.example.demo.Model;

import java.util.List;

import lombok.Data;

@Data
public class PaperSet {
	private int testId;
    private List<Integer> questionIds;

}
