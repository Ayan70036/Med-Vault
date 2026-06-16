package com.medvault.medvault_backend.repository;

import com.medvault.medvault_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmergencyCode(String emergencyCode);

    boolean existsByEmail(String email);


}