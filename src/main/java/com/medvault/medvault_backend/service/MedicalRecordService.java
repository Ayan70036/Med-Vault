package com.medvault.medvault_backend.service;

import com.medvault.medvault_backend.dto.MedicalRecordDto;
import com.medvault.medvault_backend.dto.RecordResponseDto;
import com.medvault.medvault_backend.entity.MedicalRecord;
import com.medvault.medvault_backend.entity.User;
import com.medvault.medvault_backend.repository.MedicalRecordRepository;
import com.medvault.medvault_backend.repository.UserRepository;
import com.medvault.medvault_backend.util.AesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final UserRepository userRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final AuditService auditService;
    private final AccessService accessService;

    public MedicalRecord createRecord(
            MedicalRecordDto dto) {

        boolean hasAccess =
                accessService.hasValidAccess(
                        dto.getDoctorEmail(),
                        dto.getPatientEmail());

        if (!hasAccess) {
            throw new RuntimeException(
                    "Doctor does not have access");
        }

        User doctor =
                userRepository.findByEmail(
                                dto.getDoctorEmail())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Doctor not found"));

        User patient =
                userRepository.findByEmail(
                                dto.getPatientEmail())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Patient not found"));

        String encryptedContent =
                AesUtil.encrypt(
                        dto.getContent());

        MedicalRecord record =
                MedicalRecord.builder()
                        .doctor(doctor)
                        .patient(patient)
                        .encryptedContent(
                                encryptedContent)
                        .build();

        MedicalRecord savedRecord =
                medicalRecordRepository.save(record);

        auditService.log(
                doctor.getEmail(),
                "Uploaded medical record for "
                        + patient.getEmail());

        return savedRecord;
    }

    public List<MedicalRecord> getPatientRecords(
            String patientEmail) {

        User patient = userRepository
                .findByEmail(patientEmail)
                .orElseThrow();

        return medicalRecordRepository
                .findByPatient(patient);
    }

    public List<RecordResponseDto> getDecryptedRecords(
            String doctorEmail,
            String patientEmail) {

        boolean hasAccess =
                accessService.hasValidAccess(
                        doctorEmail,
                        patientEmail);

        if (!hasAccess) {
            throw new RuntimeException(
                    "Access Denied");
        }

        User patient = userRepository
                .findByEmail(patientEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient not found"));

        List<MedicalRecord> records =
                medicalRecordRepository
                        .findByPatient(patient);

        return records.stream()
                .map(record ->
                        new RecordResponseDto(
                                record.getDoctor().getName(),
                                AesUtil.decrypt(
                                        record.getEncryptedContent()
                                ),
                                record.getCreatedAt()
                        )
                )
                .toList();
    }

    public List<RecordResponseDto> getMyDecryptedRecords(
            String patientEmail) {

        User patient = userRepository
                .findByEmail(patientEmail)
                .orElseThrow();

        return medicalRecordRepository
                .findByPatient(patient)
                .stream()
                .map(record ->
                        new RecordResponseDto(
                                record.getDoctor().getName(),
                                AesUtil.decrypt(
                                        record.getEncryptedContent()),
                                record.getCreatedAt()))
                .toList();
    }
}