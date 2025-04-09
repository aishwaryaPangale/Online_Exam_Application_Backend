package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return registerRepository.getAllStudents();
    }

    

    @GetMapping("register/{id}")
    public Register getStudentById(@PathVariable int id) {
        return registerRepository.getStudentById(id);
    }

    @PutMapping("register/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable int id, @RequestBody Register student) {
        student.setId(id);
        registerRepository.updateStudent(student);
        return ResponseEntity.ok("Student updated successfully");
    }

    @DeleteMapping("register/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
    	registerRepository.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
