package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Register;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RegisterRepoImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM students WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public boolean saveStudent(Register student) {
        String sql = "INSERT INTO students (name, email, contact, address, course, birthdate, gender, username, password,batch) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        int rowsAffected = jdbcTemplate.update(sql,
                student.getName(), student.getEmail(), student.getContact(),
                student.getAddress(), student.getCourse(), student.getBirthdate(),
                student.getGender(), student.getUsername(), student.getPassword(),student.getBatch());

        return rowsAffected > 0; 
    }

    public boolean validateLogin(String email, String username, String password) {
        String sql = "SELECT * FROM students WHERE email = ? AND username = ? AND password = ?";
        List<Register> students = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Register.class), email, username, password);
        return students.size() == 1;
    }
    
    
    
    /*curd operation*/

      public List<Register> getAllStudents(){
    	  List<Register> list;
    	  String sql = "SELECT * FROM students";
    	  list= jdbcTemplate.query(sql, new RowMapper<Register>() {

			@Override
			public Register mapRow(ResultSet rs, int rowNum) throws SQLException {
				Register reg=new Register();
				reg.setId(rs.getInt(1));
				reg.setName(rs.getString(2));
				reg.setEmail(rs.getString(3));
				reg.setContact(rs.getString(4));
				reg.setAddress(rs.getString(5));
				reg.setCourse(rs.getString(6));
				reg.setBirthdate(rs.getString(7));
				reg.setGender(rs.getString(8));
				reg.setUsername(rs.getString(9));
				reg.setPassword(rs.getString(10));
				reg.setOtp(rs.getString(11));
				reg.setBatch(rs.getString(12));
				return reg;
			}
    		  
    	  });
    	  return list;
      }
    
    public int deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Register getStudentById(int id) {
        String sql = "select * from students where id = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<Register>() {
            @Override
            public Register mapRow(ResultSet rs, int rowNum) throws SQLException {
                Register reg = new Register();
                reg.setId(rs.getInt("id"));
                reg.setName(rs.getString("name"));
                reg.setEmail(rs.getString("email"));
                reg.setContact(rs.getString("contact"));
                reg.setAddress(rs.getString("address"));
                reg.setCourse(rs.getString("course"));
                reg.setBirthdate(rs.getString("birthdate"));
                reg.setGender(rs.getString("gender"));
                reg.setUsername(rs.getString("username"));;
                reg.setPassword(rs.getString("password"));
                reg.setOtp(rs.getString("otp"));
                reg.setBatch(rs.getString("batch"));
                return reg;
            }
        }, id);
    }


    public int updateStudent(Register student) {
        String sql = "update students set name = ?, email = ?, contact = ?, birthdate = ?, gender = ?, address = ?, course = ?, batch = ?, username = ?, password = ? WHERE id = ?";

        return jdbcTemplate.update(sql,new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, student.getName());
	            ps.setString(2, student.getEmail());
	            ps.setString(3, student.getContact());
	            ps.setString(4, student.getBirthdate()); 
	            ps.setString(5, student.getGender());
	            ps.setString(6, student.getAddress());
	            ps.setString(7, student.getCourse());
	            ps.setString(8, student.getBatch());
	            ps.setString(9, student.getUsername());
	            ps.setString(10, student.getPassword());
	            ps.setInt(11, student.getId());
			}
        	
        });
    }

    
    public Register getStudentByUsername(String username) {
        String sql = "select * from students where username = ?";

        List<Register> list = jdbcTemplate.query(sql, new RowMapper<Register>() {
            @Override
            public Register mapRow(ResultSet rs, int rowNum) throws SQLException {
                Register student = new Register();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setContact(rs.getString("contact"));
                student.setBirthdate(rs.getString("birthdate")); 
                student.setGender(rs.getString("gender"));
                student.setAddress(rs.getString("address"));
                student.setCourse(rs.getString("course"));
                student.setBatch(rs.getString("batch"));
                student.setUsername(rs.getString("username"));
                student.setPassword(rs.getString("password"));
                student.setOtp(rs.getString("otp"));
                return student;
            }
        }, username);

        return list.isEmpty() ? null : list.get(0);
    }


    //update student by name
    
//    public boolean updateStudentByUsername(String username, Register student) {
//        String sql = "UPDATE students SET name = ?, email = ?, contact = ?, gender = ?, birthdate = ?, course = ?, batch = ?, address = ? WHERE username = ?";
//
//        int rows = jdbcTemplate.update(sql,
//                student.getName(),
//                student.getEmail(),
//                student.getContact(),
//                student.getGender(),
//                student.getBirthdate(),
//                student.getCourse(),
//                student.getBatch(),
//                student.getAddress(),
//                username);
//
//        return rows > 0;
//    }
    public boolean updateStudentByUsername(String username, Register student) {
        String sql = "update students set name = ?, email = ?, contact = ?, gender = ?, birthdate = ?, course = ?, batch = ?, address = ? where username = ?";

        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, student.getName());
                ps.setString(2, student.getEmail());
                ps.setString(3, student.getContact());
                ps.setString(4, student.getGender());
                ps.setString(5, student.getBirthdate()); 
                ps.setString(6, student.getCourse());
                ps.setString(7, student.getBatch());
                ps.setString(8, student.getAddress());
                ps.setString(9, username);
            }
        }) > 0; 
    }

    
   

}

