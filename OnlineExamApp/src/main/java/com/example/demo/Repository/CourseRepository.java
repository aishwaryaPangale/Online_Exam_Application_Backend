package com.example.demo.Repository;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Batch;
import com.example.demo.Model.Course;

@Repository
public class CourseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


      public int addCourse(Course course) {
    	  String sql="insert into course (course_name, course_type, course_duration, course_content) values (?, ?, ?, ?)";
    	  return jdbcTemplate.update(sql,new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, course.getCourseName());
	            ps.setString(2, course.getCourseType());
	            ps.setString(3, course.getCourseDuration());
	            ps.setString(4, course.getCourseContent());
			}
    		  
    	  });
    	  
      }
      
      

      public List<Course> getAllCourses(){
    	  List<Course> list = new ArrayList<>();
    	  String sql= "select * from course";
    	  list=jdbcTemplate.query(sql, new RowMapper<Course>() {

			@Override
			public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Course course=new Course();
				course.setId(rs.getInt(1));;
				course.setCourseName(rs.getString(2));
				course.setCourseType(rs.getString(3));
				course.setCourseDuration(rs.getString(4));
				course.setCourseContent(rs.getString(5));
				return course;
			}
    		  
    	  });
    	  return list;
      }
      
  
      public int updateCourse(Course course) {
    	    String sql = "update course set course_name = ?, course_type = ?, course_duration = ?, course_content = ? WHERE id = ?";
    	    
    	    return jdbcTemplate.update(sql, new PreparedStatementSetter() {
    	        @Override
    	        public void setValues(PreparedStatement ps) throws SQLException {
    	            ps.setString(1, course.getCourseName());
    	            ps.setString(2, course.getCourseType());
    	            ps.setString(3, course.getCourseDuration());
    	            ps.setString(4, course.getCourseContent());
    	            ps.setInt(5, course.getId());
    	        }
    	    });
    	}

      
    public int deleteCourse(int id) {
        String sql = "DELETE FROM course WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }

    
    public List<Course> searchByName(String name) {
        String sql = "SELECT * FROM course WHERE course_name LIKE ?";

        String searchPattern = "%" + name + "%";
       Object[] params = { searchPattern };

        return jdbcTemplate.query(sql, params, new RowMapper<Course>() {
            @Override
            public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setCourseName(rs.getString("course_name"));
                course.setCourseType(rs.getString("course_type"));
                course.setCourseDuration(rs.getString("course_duration"));
                course.setCourseContent(rs.getString("course_content"));
                return course;
            }
        });
    }

}

