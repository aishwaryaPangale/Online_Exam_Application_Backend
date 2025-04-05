package com.example.demo.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Course;
import com.example.demo.Repository.CourseRepository;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> getCourses() {
        return courseRepository.getAllCourses();
    }

    @PostMapping
    public String addCourse(@RequestBody Course course) {
        int result = courseRepository.addCourse(course);
        return result > 0 ? "Course added successfully" : "Error adding course";
    }

    @PutMapping("/{id}")
    public String updateCourse(@PathVariable int id, @RequestBody Course course) {
        int result = courseRepository.updateCourse(id, course);
        return result > 0 ? "Course updated successfully" : "Error updating course";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable int id) {
        int result = courseRepository.deleteCourse(id);
        return result > 0 ? "Course deleted successfully" : "Error deleting course";
    }

    @GetMapping("/search/{keyword}")
    public List<Course> searchCourses(@PathVariable String keyword) {
        return courseRepository.searchCourses(keyword);
    }
}
