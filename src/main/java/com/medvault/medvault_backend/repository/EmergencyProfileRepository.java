package com.medvault.medvault_backend.repository;

import com.medvault.medvault_backend.entity.EmergencyProfile;
import com.medvault.medvault_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmergencyProfileRepository
        extends JpaRepository<EmergencyProfile, Long> {

    Optional<EmergencyProfile> findByPatient(User patient);
}