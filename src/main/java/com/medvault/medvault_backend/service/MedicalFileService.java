package com.medvault.medvault_backend.service;

import com.medvault.medvault_backend.dto.FileResponseDto;
import com.medvault.medvault_backend.entity.MedicalFile;
import com.medvault.medvault_backend.entity.User;
import com.medvault.medvault_backend.repository.MedicalFileRepository;
import com.medvault.medvault_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicalFileService {

    private final MedicalFileRepository medicalFileRepository;
    private final UserRepository userRepository;
    private final AccessService accessService;
    private final AuditService auditService;

    private static final String UPLOAD_DIR =
            System.getProperty("user.dir")
                    + "/uploads/";

    public String uploadFile(
            MultipartFile file,
            String doctorEmail,
            String patientEmail) {

        try {

            boolean hasAccess =
                    accessService.hasValidAccess(
                            doctorEmail,
                            patientEmail);

            if (!hasAccess) {
                throw new RuntimeException(
                        "Access Expired");
            }

            User doctor =
                    userRepository.findByEmail(
                                    doctorEmail)
                            .orElseThrow();

            User patient =
                    userRepository.findByEmail(
                                    patientEmail)
                            .orElseThrow();

            String originalName =
                    file.getOriginalFilename();

            String storedName =
                    UUID.randomUUID()
                            + "_"
                            + originalName;

            File uploadDir = new File(UPLOAD_DIR);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            File destination =
                    new File(uploadDir, storedName);

            file.transferTo(destination);
            MedicalFile medicalFile =
                    MedicalFile.builder()
                            .doctor(doctor)
                            .patient(patient)
                            .originalName(
                                    originalName)
                            .storedName(
                                    storedName)
                            .filePath(
                                    destination
                                            .getAbsolutePath())
                            .build();

            medicalFileRepository.save(medicalFile);

            auditService.log(
                    doctor.getEmail(),
                    "Uploaded file "
                            + originalName);

            return "File Uploaded Successfully";

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public List<FileResponseDto> getPatientFiles(
            String patientEmail) {

        User patient =
                userRepository.findByEmail(
                                patientEmail)
                        .orElseThrow();

        List<MedicalFile> files =
                medicalFileRepository
                        .findByPatient(patient);

        return files.stream()
                .map(file ->
                        new FileResponseDto(
                                file.getId(),
                                file.getOriginalName(),
                                file.getDoctor()
                                        .getName()
                        )
                )
                .toList();
    }

    public MedicalFile getFile(Long fileId) {

        return medicalFileRepository
                .findById(fileId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "File not found"));
    }

    public MedicalFile getFileForDoctor(
            Long fileId,
            String doctorEmail,
            String patientEmail) {

        if (!accessService.hasValidAccess(
                doctorEmail,
                patientEmail)) {

            throw new RuntimeException(
                    "Access Expired");
        }

        return medicalFileRepository
                .findById(fileId)
                .orElseThrow();
    }

    public List<FileResponseDto> getFilesForDoctor(
            String doctorEmail,
            String patientEmail) {

        if (!accessService.hasValidAccess(
                doctorEmail,
                patientEmail)) {

            throw new RuntimeException(
                    "Access Denied");
        }

        User patient =
                userRepository
                        .findByEmail(patientEmail)
                        .orElseThrow();

        return medicalFileRepository
                .findByPatient(patient)
                .stream()
                .map(file ->
                        new FileResponseDto(
                                file.getId(),
                                file.getOriginalName(),
                                file.getDoctor().getName()))
                .toList();
    }
}