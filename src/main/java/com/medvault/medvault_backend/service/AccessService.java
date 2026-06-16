package com.medvault.medvault_backend.service;

import com.medvault.medvault_backend.dto.AccessRequestDto;
import com.medvault.medvault_backend.dto.DoctorPatientDto;
import com.medvault.medvault_backend.entity.*;
import com.medvault.medvault_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessService {

    private final AuditService auditService;
    private final UserRepository userRepository;
    private final AccessRequestRepository accessRequestRepository;
    private final AccessGrantRepository accessGrantRepository;

    public AccessRequest createRequest(
            AccessRequestDto dto) {

        User doctor = userRepository.findByEmail(
                        dto.getDoctorEmail())
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        User patient = userRepository.findByEmail(
                        dto.getPatientEmail())
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        AccessGrant existingGrant =
                accessGrantRepository
                        .findByDoctorAndPatient(
                                doctor,
                                patient)
                        .orElse(null);

        if (existingGrant != null &&
                LocalDateTime.now()
                        .isBefore(
                                existingGrant.getExpiresAt())) {

            throw new RuntimeException(
                    "Doctor already has access");
        }

        AccessRequest request =
                AccessRequest.builder()
                        .doctor(doctor)
                        .patient(patient)
                        .requestedDays(dto.getRequestedDays())
                        .status(RequestStatus.PENDING)
                        .build();

        AccessRequest savedRequest =
                accessRequestRepository.save(request);

        auditService.log(
                doctor.getEmail(),
                "Requested access for "
                        + patient.getEmail());

        return savedRequest;
    }

    public List<AccessRequest> getPatientRequests(
            String email) {

        User patient = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        return accessRequestRepository.findByPatient(patient);
    }

    public String approveRequest(
            Long requestId) {

        AccessRequest request =
                accessRequestRepository.findById(requestId)
                        .orElseThrow(() ->
                                new RuntimeException("Request not found"));

        request.setStatus(RequestStatus.APPROVED);

        accessRequestRepository.save(request);

        AccessGrant grant =
                AccessGrant.builder()
                        .doctor(request.getDoctor())
                        .patient(request.getPatient())
                        .expiresAt(
                                LocalDateTime.now()
                                        .plusDays(
                                                request.getRequestedDays()))
                        .build();

        accessGrantRepository.save(grant);

        auditService.log(
                request.getPatient().getEmail(),
                "Approved access for "
                        + request.getDoctor().getEmail());

        return "Access Approved";
    }
    @Transactional
    public String rejectRequest(
            Long requestId) {

        AccessRequest request =
                accessRequestRepository
                        .findById(requestId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Request not found"));


        if (request.getStatus()
                == RequestStatus.APPROVED) {

            AccessGrant grant =
                    accessGrantRepository
                            .findByDoctorAndPatient(
                                    request.getDoctor(),
                                    request.getPatient())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Grant not found"));

            System.out.println(
                    "Grant Found: "
                            + grant.getId());

            accessGrantRepository.delete(grant);

            System.out.println(
                    "Grant Deleted");

            request.setStatus(
                    RequestStatus.REJECTED);

            accessRequestRepository
                    .save(request);

            auditService.log(
                    request.getPatient().getEmail(),
                    "Revoked access for "
                            + request.getDoctor().getEmail());

            return "Access Revoked";



    }

        request.setStatus(
                RequestStatus.REJECTED);

        accessRequestRepository
                .save(request);

        return "Access Rejected";
    }

    public boolean hasValidAccess(
            String doctorEmail,
            String patientEmail) {

        User doctor = userRepository
                .findByEmail(doctorEmail)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        User patient = userRepository
                .findByEmail(patientEmail)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        AccessGrant grant =
                accessGrantRepository
                        .findByDoctorAndPatient(
                                doctor,
                                patient)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "No access grant found"));

        return LocalDateTime.now()
                .isBefore(grant.getExpiresAt());
    }

    public List<DoctorPatientDto> getDoctorPatients(
            String doctorEmail) {

        User doctor =
                userRepository.findByEmail(
                                doctorEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Doctor not found"));

        return accessGrantRepository
                .findByDoctor(doctor)
                .stream()
                .filter(grant ->
                        LocalDateTime.now()
                                .isBefore(
                                        grant.getExpiresAt()))
                .map(grant ->
                        new DoctorPatientDto(
                                grant.getPatient().getId(),
                                grant.getPatient().getName(),
                                grant.getPatient().getEmail()
                        ))
                .toList();
    }



}