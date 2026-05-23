package com.example.hospital_api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WardService {

    @Autowired
    private WardRepository repository;

    public List<WardRecord> getAllRecords() {
        return repository.findAll();
    }

    public WardRecord saveRecord(WardRecord record) {
        // New daily rates
        int dailyRate = 0;
        if ("Private".equalsIgnoreCase(record.getCategory()))
            dailyRate = 2500;
        else if ("General".equalsIgnoreCase(record.getCategory()))
            dailyRate = 1000;
        else if ("Emergency".equalsIgnoreCase(record.getCategory()))
            dailyRate = 3500;

        // Calculate Total Fee: Daily Rate * Number of Days
        record.setFee(dailyRate * record.getDays());

        return repository.save(record);
    }
}