package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.PaperSet;
import com.example.demo.Repository.PaperSetRepository;

@Service
public class PaperSetService {
	@Autowired
    private PaperSetRepository repository;

    public PaperSet createPaperSet(PaperSet paperSet) {
        int generatedId = repository.save(paperSet);
        paperSet.setId(generatedId);
        return paperSet;
    }
}
