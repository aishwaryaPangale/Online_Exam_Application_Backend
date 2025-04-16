package com.example.demo.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Batch;

@Repository
public class BatchRepository {
	 @Autowired
	    private JdbcTemplate jdbcTemplate;

	 public void save(Batch batch) {
	        String sql = "INSERT INTO batch (batch_name, course_id) VALUES (?, ?)";
	        jdbcTemplate.update(sql, batch.getBatchName(), batch.getCourseId());
	    }

	 public List<Map<String, Object>> getAllBatches() {
		    String sql = "SELECT b.id AS batch_id, b.batch_name, c.course_name FROM batch b JOIN course c ON b.course_id = c.id";
		    return jdbcTemplate.queryForList(sql);
		}

	    public List<Batch> searchBatch(String keyword) {
	        String sql = "SELECT * FROM batch WHERE batch_name LIKE ? OR course_name LIKE ?";
	        String searchPattern = "%" + keyword + "%";
	        return jdbcTemplate.query(sql, new Object[]{searchPattern, searchPattern},
	                new BeanPropertyRowMapper<>(Batch.class));
	    }

}
