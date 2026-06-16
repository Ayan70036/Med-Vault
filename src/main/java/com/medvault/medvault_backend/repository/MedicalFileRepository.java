package com.medvault.medvault_backend.repository;

import com.medvault.medvault_backend.entity.MedicalFile;
import com.medvault.medvault_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalFileRepository
        extends JpaRepository<MedicalFile, Long> {

    List<MedicalFile> findByPatient(User patient);
}