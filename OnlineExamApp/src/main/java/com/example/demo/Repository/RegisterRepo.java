package com.example.demo.Repository;

import com.example.demo.Model.Register;

public interface RegisterRepo {
	  boolean isEmailRegistered(String email, String role);
	void saveUser(Register reg);
}
