package com.example.demo.Model;

import lombok.Data;

@Data
public class Course {
    private int id;
    private String name;
    private String description;

    public Course() {}

    public Course(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}

