package com.example.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Register;
import com.example.demo.Repository.RegisterRepoImpl;

@Service
public class RegisterServiceImpl {

    @Autowired
    private RegisterRepoImpl regRepo;

    public String register(Register student) {
        if (regRepo.emailExists(student.getEmail())) {
            return "Email already exists";
        }
        boolean success = regRepo.saveStudent(student);
        return success ? "Success" : "Failed";
    }

    public boolean login(String email, String username, String password) {
        return regRepo.validateLogin(email, username, password);
    }
    
    
    //curd operation
    public List<Register> getAllStudents() {
        return regRepo.getAllStudents();
    }

    public Register getStudentById(int id) {
        return regRepo.getStudentById(id);
    }


    public void updateStudent(int id, Register student) {
        student.setId(id);
        regRepo.updateStudent(student);
    }

    public void deleteStudent(int id) {
    	regRepo.deleteStudent(id);
    }
    public Register getStudentByUsername(String username) {
        return regRepo.getStudentByUsername(username);
    }
    
    public boolean updateStudent(String username, Register updatedStudent) {
        return regRepo.updateStudentByUsername(username, updatedStudent);
    }

}
