package com.medvault.medvault_backend.repository;

import com.medvault.medvault_backend.entity.AccessRequest;
import com.medvault.medvault_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRequestRepository
        extends JpaRepository<AccessRequest, Long> {

    List<AccessRequest> findByPatient(User patient);

    List<AccessRequest> findByDoctor(User doctor);
}