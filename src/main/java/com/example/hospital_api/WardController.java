package com.example.hospital_api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marks this as an API controller [cite: 38]
@RequestMapping("/api/wards")
public class WardController {

    @Autowired
    private WardService service;

    // Equivalent to your old "Load Patient Records" button
    @GetMapping // Creates an endpoint for GET requests [cite: 39]
    public List<WardRecord> getAllWards() {
        return service.getAllRecords();
    }

    // Equivalent to your old "Add Record" button
    @PostMapping
    public WardRecord addWard(@RequestBody WardRecord newRecord) {
        return service.saveRecord(newRecord);
    }
}
