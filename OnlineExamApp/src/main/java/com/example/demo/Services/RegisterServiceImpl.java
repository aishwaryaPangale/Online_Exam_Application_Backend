package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Register;
import com.example.demo.Repository.RegisterRepoImpl;


@Service
public class RegisterServiceImpl{

	@Autowired
	private RegisterRepoImpl regRepo;

	 public String register(Register student) {
	        if (regRepo.emailExists(student.getEmail())) {
	            return "Email already exists";
	        }
	        regRepo.saveStudent(student);
	        return "Success";
	    }

	    public boolean login(String email, String username, String password) {
	        return regRepo.validateLogin(email, username, password);
	    }


	
}
