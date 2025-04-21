package com.example.demo.Controller;

import com.example.demo.Model.Course;
import com.example.demo.Services.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin("http://localhost:5173")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public String addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return "Course added successfully!";
    }


    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PutMapping("/update/{id}")
    public String updateCourse(@PathVariable int id , @RequestBody Course course) {
    	course.setId(id);
        courseService.updateCourse(course);
        return "Course updated successfully!";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return "Course deleted successfully!";
    }

    @GetMapping("/search")
    public List<Course> searchCourse(@RequestParam String name) {
        return courseService.searchByName(name);
    }
    
    @GetMapping("/count")
    public Map<String, Integer> getCourseCount() {
        int count = courseService.getCourseCount();
        return Map.of("count", count);
    }
}
