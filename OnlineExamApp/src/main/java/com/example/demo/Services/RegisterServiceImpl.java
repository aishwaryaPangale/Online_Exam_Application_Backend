package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Register;
import com.example.demo.Repository.RegisterRepo;

@Service
public class RegisterServiceImpl implements RegisterService{

	@Autowired
	private RegisterRepo regRepo;


	public String registerUser(Register user) {
        if (regRepo.isEmailRegistered(user.getEmail(), user.getRole())) {
            return "Email already exists";
        }
        regRepo.saveUser(user);
        return "User registered successfully";
    }


	
}
