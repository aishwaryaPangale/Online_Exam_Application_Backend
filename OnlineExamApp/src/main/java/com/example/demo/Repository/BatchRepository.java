package com.example.demo.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Batch;

@Repository
public class BatchRepository {
	 @Autowired
	    private JdbcTemplate jdbcTemplate;

//	 public void save(Batch batch) {
//	        String sql = "insert into batch (batch_name, course_id) values (?, ?)";
//	        jdbcTemplate.update(sql, batch.getBatchName(), batch.getCourseId());
//	    }
	  public int save(Batch batch) {
		  String sql ="insert into batch (batch_name, course_id) values (?, ?)";
		 return jdbcTemplate.update(sql, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, batch.getBatchName());
				ps.setInt(2, batch.getCourseId());
			}
			  
		  });
	  }
	 
	 public List<Batch> getAllBatches() {
		    List<Batch> list = new ArrayList<>();

		    String sql = "select b.id, b.batch_name, c.course_name from batch b join course c on b.course_id = c.id";

		    list = jdbcTemplate.query(sql, new RowMapper<Batch>() {
		        @Override
		        public Batch mapRow(ResultSet rs, int rowNum) throws SQLException {
		            Batch batch = new Batch();
		            batch.setId(rs.getInt(1));
		            batch.setBatchName(rs.getString(2));
		            batch.setCourseName(rs.getString(3)); 
		            return batch;
		        }
		    });

		    return list;
		}



	 public List<Batch> searchBatch(String keyword) {
		    List<Batch> list = new ArrayList<>();

		    String sql = "select b.id, b.batch_name, c.course_name from batch b join course c on b.course_id = c.id where b.batch_name LIKE ? OR c.course_name LIKE ?";

		    String searchPattern = "%" + keyword + "%";

		    list = jdbcTemplate.query(sql, new Object[]{searchPattern, searchPattern}, new RowMapper<Batch>() {
		        @Override
		        public Batch mapRow(ResultSet rs, int rowNum) throws SQLException {
		            Batch batch = new Batch();
		            batch.setId(rs.getInt(1));
		            batch.setBatchName(rs.getString(2));
		            batch.setCourseName(rs.getString(3)); 
		            return batch;
		        }
		    });

		    return list;
		}
	 
	 public int fetchBatchCount() {
		    String sql = "SELECT COUNT(*) FROM batch";
		    List<Integer> result = jdbcTemplate.query(sql, new RowMapper<Integer>() {
		        @Override
		        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
		            return rs.getInt(1); // return the count
		        }
		    });

		    return result.isEmpty() ? 0 : result.get(0); // if empty, return 0
		}



}
