package com.example.hospitalapi;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class WardRecord {

    @Id
    private int wardNo;
    private String category;
    private String patientName;
    private String doctorName;

    // Forces the backend to send and receive dates as standard strings
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionDate;

    private boolean isActive = true;

    // --- GETTERS AND SETTERS ---
    public int getWardNo() {
        return wardNo;
    }

    public void setWardNo(int wardNo) {
        this.wardNo = wardNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    // --- DYNAMIC MATH ---
    public int getDays() {
        if (admissionDate == null)
            return 1;
        long daysBetween = ChronoUnit.DAYS.between(admissionDate, LocalDate.now());
        // If admitted today, or if date glitch, charge for at least 1 day
        return daysBetween <= 0 ? 1 : (int) daysBetween;
    }

    public int getFee() {
        int rate = 0;
        if ("Private".equals(category))
            rate = 2500;
        else if ("General".equals(category))
            rate = 1000;
        else if ("Emergency".equals(category))
            rate = 5000;
        return getDays() * rate;
    }
}