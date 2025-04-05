package com.example.demo.Controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Register;
import com.example.demo.Services.RegisterService;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173")
public class RegisterController {
	@Autowired
	private RegisterService regService;
	 @PostMapping("/register")
	    public String registerUser(@RequestBody Register reg ) {
		 return regService.registerUser(reg);
	    }
}
