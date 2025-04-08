package com.example.demo.Services;

import com.example.demo.Model.Register;
import com.example.demo.Repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public List<Register> findByEmail(String email) {
        return loginRepository.findByEmail(email);
    }

    public List<Register> findByEmailAndUsernameAndPassword(String email, String username, String password) {
        return loginRepository.findByEmailAndUsernameAndPassword(email, username, password);
    }

    public void updateOtpByEmail(String email, String otp) {
        loginRepository.updateOtpByEmail(email, otp);
    }
}
