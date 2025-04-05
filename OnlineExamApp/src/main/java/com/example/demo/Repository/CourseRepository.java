package com.example.demo.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Course;

import java.util.List;

@Repository
public class CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    public CourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Course> getAllCourses() {
        return jdbcTemplate.query("SELECT * FROM courses",
            (rs, rowNum) -> new Course(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
    }

    public int addCourse(Course course) {
        return jdbcTemplate.update("INSERT INTO courses (name, description) VALUES (?, ?)",
                course.getName(), course.getDescription());
    }

    public int updateCourse(int id, Course course) {
        return jdbcTemplate.update("UPDATE courses SET name = ?, description = ? WHERE id = ?",
                course.getName(), course.getDescription(), id);
    }

    public int deleteCourse(int id) {
        return jdbcTemplate.update("DELETE FROM courses WHERE id = ?", id);
    }

    public List<Course> searchCourses(String keyword) {
        String sql = "SELECT * FROM courses WHERE name LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + keyword + "%"},
            (rs, rowNum) -> new Course(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
    }
}
