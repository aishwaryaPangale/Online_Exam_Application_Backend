package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.Repository.AdminLoginRepository;

@Repository
public class AdminLoginService {

	@Autowired
    private AdminLoginRepository adminRepository;

    public boolean login(String username, String password) {
        return adminRepository.validateAdmin(username, password);
    }
}
