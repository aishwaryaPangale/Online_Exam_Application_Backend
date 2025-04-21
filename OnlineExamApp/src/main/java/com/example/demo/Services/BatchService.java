package com.example.demo.Services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Batch;
import com.example.demo.Repository.BatchRepository;

@Service
public class BatchService {

	  @Autowired
	    private BatchRepository batchRepo;

	  public void addBatch(Batch batch) {
		  batchRepo.save(batch);
	    }

	  public List<Batch> getAllBatches() {
		    return batchRepo.getAllBatches();
		}


	    public List<Batch> searchBatch(String keyword) {
	        return batchRepo.searchBatch(keyword);
	    }
	    
	    public int getBatchCount() {
	        return batchRepo.fetchBatchCount();
	    }

}
