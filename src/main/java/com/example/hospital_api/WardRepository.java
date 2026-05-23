package com.example.hospital_api;

import org.springframework.data.jpa.repository.JpaRepository;

// By extending JpaRepository, Spring gives us save(), findAll(), etc. for free! [cite: 54, 55, 56]
public interface WardRepository extends JpaRepository<WardRecord, Integer> {
}
