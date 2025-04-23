package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.Model.Register;
import com.example.demo.Repository.RegisterRepoImpl;
import com.example.demo.Services.RegisterServiceImpl;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") 
public class RegisterController {

    @Autowired
    private RegisterServiceImpl regService;

    @Autowired
    private RegisterRepoImpl registerRepository;

//    @PostMapping("/register")
//    public String register(@RequestBody Register student) {
//        return regService.register(student);
//    }
    @PostMapping("/register")
    public String register(@RequestBody Register student) {
        if (registerRepository.emailExists(student.getEmail())) {
            return "Email already exists";
        }

        boolean saved = registerRepository.saveStudent(student);
        if (saved) {
            return "Registered successfully";
        } else {
            return "Error saving student";
        }
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String username,  @RequestParam String password) {
        boolean isValidLogin = regService.login(email, username, password);

        if (isValidLogin) {
            return "Login successful!";
        } else {
            return "Invalid credentials!";
        }
    }
    //curd operation
    @GetMapping("/register/all")
    public List<Register> getAllStudents() {
        return regService.getAllStudents();
    }

    

    @GetMapping("register/{id}")
    public Register getStudentById(@PathVariable int id) {
        return regService.getStudentById(id);
    }

    @PutMapping("register/{id}")
    public String updateStudent(@PathVariable int id, @RequestBody Register student) {
        student.setId(id);
        regService.updateStudent(student);
        return "Student updated successfully";
    }

    @DeleteMapping("register/{id}")
    public String deleteStudent(@PathVariable int id) {
    	registerRepository.deleteStudent(id);
        return "Student deleted successfully";
    }
    @GetMapping("/register/username/{username}")
    public Register getStudentByUsername(@PathVariable String username) {
        Register user = regService.getStudentByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return user;
    }
    
    //update student by name
    @PutMapping("/register/update/{username}")
    public String updateStudent(@PathVariable String username, @RequestBody Register updatedStudent) {
        boolean success = regService.updateStudent(username, updatedStudent);
        if (success) {
            return "Updated successfully";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "update failed");
        }
    }
    
    //count student
    @GetMapping("/students/count")
    public Map<String, Integer> getStudentCount() {
        int count = regService.getStudentCount();
        return Map.of("count", count);
    }
    
    //test Score
//    @GetMapping("/students/{studentId}/tests")
//    public List<Register> getStudentTests(@PathVariable int studentId) {
//        return regService.getStudentTests(studentId);
//    }


}
