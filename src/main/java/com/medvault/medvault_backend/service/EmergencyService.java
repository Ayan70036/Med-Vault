package com.medvault.medvault_backend.service;

import com.medvault.medvault_backend.dto.EmergencyProfileDto;
import com.medvault.medvault_backend.entity.EmergencyProfile;
import com.medvault.medvault_backend.entity.User;
import com.medvault.medvault_backend.repository.EmergencyProfileRepository;
import com.medvault.medvault_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmergencyService {

    private final EmergencyProfileRepository emergencyProfileRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;

    public EmergencyProfile saveProfile(
            EmergencyProfileDto dto) {

        User patient =
                userRepository.findByEmail(
                                dto.getPatientEmail())
                        .orElseThrow();

        EmergencyProfile profile =
                emergencyProfileRepository
                        .findByPatient(patient)
                        .orElse(
                                EmergencyProfile.builder()
                                        .patient(patient)
                                        .build()
                        );

        profile.setBloodGroup(
                dto.getBloodGroup());

        profile.setAllergies(
                dto.getAllergies());

        profile.setConditions(
                dto.getConditions());

        profile.setEmergencyContact(
                dto.getEmergencyContact());

        EmergencyProfile savedProfile =
                emergencyProfileRepository.save(profile);

        auditService.log(
                patient.getEmail(),
                "Updated emergency profile");

        return savedProfile;
    }

    public EmergencyProfile getProfileByCode(
            String emergencyCode) {

        User patient =
                userRepository.findByEmergencyCode(
                                emergencyCode)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid Emergency Code"));

        return emergencyProfileRepository
                .findByPatient(patient)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Emergency Profile Not Found"));
    }

    public EmergencyProfile getProfileByEmail(
            String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        return emergencyProfileRepository
                .findByPatient(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Profile not found"));
    }
}