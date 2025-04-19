package com.example.demo.Services;

import com.example.demo.Model.Course;
import com.example.demo.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public void addCourse(Course course) {
         courseRepository.addCourse(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }

    public void updateCourse(Course course) {
        courseRepository.updateCourse(course);
    }

    public void deleteCourse(int id) {
        courseRepository.deleteCourse(id);
    }

    public List<Course> searchByName(String name) {
        return courseRepository.searchByName(name);
    }
}
