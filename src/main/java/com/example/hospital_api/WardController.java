package com.example.hospitalapi;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wards")
@CrossOrigin(origins = "*")
public class WardController {

    @Autowired
    private WardRepository repository;

    // GET: Only pull active patients for the dashboard
    @GetMapping
    public List<WardRecord> getAllActive() {
        return repository.findAll().stream()
                .filter(WardRecord::getIsActive)
                .collect(Collectors.toList());
    }

    // POST: Add new patient
    @PostMapping
    public WardRecord addPatient(@RequestBody WardRecord record) {
        if (record.getAdmissionDate() == null) {
            record.setAdmissionDate(java.time.LocalDate.now());
        }
        return repository.save(record);
    }

    // PUT: Edit existing patient
    @PutMapping("/{wardNo}")
    public WardRecord updatePatient(@PathVariable int wardNo, @RequestBody WardRecord updatedRecord) {
        return repository.findById(wardNo).map(ward -> {
            ward.setPatientName(updatedRecord.getPatientName());
            ward.setDoctorName(updatedRecord.getDoctorName());
            ward.setCategory(updatedRecord.getCategory());
            if (updatedRecord.getAdmissionDate() != null) {
                ward.setAdmissionDate(updatedRecord.getAdmissionDate());
            }
            return repository.save(ward);
        }).orElseThrow(() -> new RuntimeException("Ward not found"));
    }

    // PUT: Soft delete (Discharge)
    @PutMapping("/{wardNo}/discharge")
    public WardRecord dischargePatient(@PathVariable int wardNo) {
        return repository.findById(wardNo).map(ward -> {
            ward.setIsActive(false);
            return repository.save(ward);
        }).orElseThrow(() -> new RuntimeException("Ward not found"));
    }
}