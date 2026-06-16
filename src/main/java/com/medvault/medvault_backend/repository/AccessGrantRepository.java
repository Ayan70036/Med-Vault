package com.medvault.medvault_backend.repository;

import com.medvault.medvault_backend.entity.AccessGrant;
import com.medvault.medvault_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccessGrantRepository
        extends JpaRepository<AccessGrant, Long> {

    List<AccessGrant> findByDoctor(User doctor);

    List<AccessGrant> findByPatient(User patient);


    Optional<AccessGrant> findByDoctorAndPatient(
            User doctor,
            User patient
    );

    void deleteByDoctorAndPatient(
            User doctor,
            User patient);
}