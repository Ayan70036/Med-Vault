package com.medvault.medvault_backend.repository;

import com.medvault.medvault_backend.entity.MedicalRecord;
import com.medvault.medvault_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository
        extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatient(User patient);
}