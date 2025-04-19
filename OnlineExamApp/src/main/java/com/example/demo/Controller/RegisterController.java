package com.example.demo.Controller;

import java.util.List;

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
@CrossOrigin(origins = "http://localhost:5173") // allow frontend port
public class RegisterController {

    @Autowired
    private RegisterServiceImpl regService;

    @Autowired
    private RegisterRepoImpl registerRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Register student) {
        if (registerRepository.emailExists(student.getEmail())) {
            return ResponseEntity.ok("Email already exists");
        }

        boolean saved = registerRepository.saveStudent(student);
        if (saved) {
            return ResponseEntity.ok("Registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving student");
        }
    }
    
    
    //curd operation
    @GetMapping("/register")
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

}
