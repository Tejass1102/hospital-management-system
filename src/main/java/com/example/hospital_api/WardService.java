package com.example.hospital_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WardService {

    @Autowired
    private WardRepository repository;

    // The old math and setFee() used to be here!
    // Since WardRecord now calculates the fee automatically based on the
    // admission/discharge dates,
    // this service simply acts as a safe bridge to the database.

    public WardRecord saveWard(WardRecord record) {
        return repository.save(record);
    }
}