package com.example.hospitalapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/wards")
@CrossOrigin(origins = "*")
public class WardController {

    @Autowired
    private WardRepository repository;

    // Fetch ONLY active patients
    @GetMapping
    public List<WardRecord> getAllActive() {
        return repository.findByIsActiveTrue();
    }

    @PostMapping
    public WardRecord addPatient(@RequestBody WardRecord record) {
        return repository.save(record);
    }

    // NEW: Update an existing patient's details
    @PutMapping("/{wardNo}")
    public WardRecord updatePatient(@PathVariable int wardNo, @RequestBody WardRecord updatedRecord) {
        return repository.findById(wardNo).map(ward -> {
            ward.setPatientName(updatedRecord.getPatientName());
            ward.setDoctorName(updatedRecord.getDoctorName());
            ward.setCategory(updatedRecord.getCategory());
            return repository.save(ward);
        }).orElseThrow(() -> new RuntimeException("Ward not found"));
    }

    // NEW: Discharge a patient (Soft Delete)
    @PutMapping("/{wardNo}/discharge")
    public WardRecord dischargePatient(@PathVariable int wardNo) {
        return repository.findById(wardNo).map(ward -> {
            ward.setActive(false); // Mark as discharged
            return repository.save(ward);
        }).orElseThrow(() -> new RuntimeException("Ward not found"));
    }
}