package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Batch;
import com.example.demo.Services.BatchService;
@RestController
@RequestMapping("/api/batches")
@CrossOrigin("http://localhost:5173")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @PostMapping("/add")
    public ResponseEntity<String> addBatch(@RequestBody Batch batch) {
        batchService.addBatch(batch);
        return ResponseEntity.ok("Batch added successfully");
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getAllBatches() {
        return batchService.getAllBatches();
    }

    // Search batch by keyword
    @GetMapping("/search")
    public List<Batch> searchBatch(@RequestParam String keyword) {
        return batchService.searchBatch(keyword);
    }
}
