package com.example.hospital_api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WardRepository extends JpaRepository<WardRecord, Integer> {
    // NEW: Only fetches patients who haven't been discharged
    List<WardRecord> findByIsActiveTrue();
}