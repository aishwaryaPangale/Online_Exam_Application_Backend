package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.PaperSet;
import com.example.demo.Services.PaperSetService;

@RestController
@RequestMapping("/api/paperset")
@CrossOrigin("http://localhost:5173")
public class PaperSetController {
	@Autowired
    private PaperSetService service;

    @PostMapping
    public PaperSet createPaperSet(@RequestBody PaperSet paperSet) {
        return service.createPaperSet(paperSet);
    }
}
