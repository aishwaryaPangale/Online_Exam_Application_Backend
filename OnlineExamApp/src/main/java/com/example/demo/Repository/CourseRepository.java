package com.example.demo.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Course;

@Repository
public class CourseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addCourse(Course course) {
        String sql = "INSERT INTO course (course_name, course_type, course_duration, course_content) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, course.getCourseName(), course.getCourseType(), course.getCourseDuration(), course.getCourseContent());
    }

    public List<Course> getAllCourses() {
        String sql = "SELECT * FROM course";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Course.class));
    }

    public Course getCourseById(int id) {
        String sql = "SELECT * FROM course WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Course.class), id);
    }

    public int updateCourse(Course course) {
        String sql = "UPDATE course SET course_name=?, course_type=?, course_duration=?, course_content=? WHERE id=?";
        return jdbcTemplate.update(sql, course.getCourseName(), course.getCourseType(), course.getCourseDuration(), course.getCourseContent(), course.getId());
    }

    public int deleteCourse(int id) {
        String sql = "DELETE FROM course WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Course> searchByName(String name) {
        String sql = "SELECT * FROM course WHERE course_name LIKE ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Course.class), "%" + name + "%");
    }
}

