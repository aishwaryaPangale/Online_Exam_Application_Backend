package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Course;
import com.example.demo.Repository.CourseRepository;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin("*")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        courseRepository.addCourse(course);
        return ResponseEntity.ok("Course added successfully!");
    }

    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable int id) {
        return courseRepository.getCourseById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCourse(@RequestBody Course course) {
        courseRepository.updateCourse(course);
        return ResponseEntity.ok("Course updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        courseRepository.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully!");
    }

    @GetMapping("/search")
    public List<Course> searchCourse(@RequestParam String name) {
        return courseRepository.searchByName(name);
    }
}
