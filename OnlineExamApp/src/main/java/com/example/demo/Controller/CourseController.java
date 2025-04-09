package com.example.demo.Controller;

import com.example.demo.Model.Course;
import com.example.demo.Services.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin("http://localhost:5173")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return ResponseEntity.ok("Course added successfully!");
    }

    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCourse(@RequestBody Course course) {
        courseService.updateCourse(course);
        return ResponseEntity.ok("Course updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully!");
    }

    @GetMapping("/search")
    public List<Course> searchCourse(@RequestParam String name) {
        return courseService.searchByName(name);
    }
}
