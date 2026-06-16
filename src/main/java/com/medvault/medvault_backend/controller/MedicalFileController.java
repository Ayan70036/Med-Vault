package com.medvault.medvault_backend.controller;

import com.medvault.medvault_backend.dto.FileResponseDto;
import com.medvault.medvault_backend.service.MedicalFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class MedicalFileController {

    private final MedicalFileService service;

    @PostMapping("/upload")
    public String uploadFile(

            @RequestParam MultipartFile file,

            @RequestParam String doctorEmail,

            @RequestParam String patientEmail) {

        return service.uploadFile(
                file,
                doctorEmail,
                patientEmail);
    }

    @GetMapping("/patient/{email}")
    public List<FileResponseDto> getFiles(
            @PathVariable String email) {

        return service.getPatientFiles(email);
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long id,
            @RequestParam String doctorEmail,
            @RequestParam String patientEmail) {

        var file =
                service.getFileForDoctor(
                        id,
                        doctorEmail,
                        patientEmail);

        Resource resource =
                new FileSystemResource(
                        file.getFilePath());

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + file.getOriginalName()
                                + "\"")
                .body(resource);
    }
    @GetMapping("/patient/download/{id}")
    public ResponseEntity<Resource> patientDownload(
            @PathVariable Long id) {

        var file = service.getFile(id);

        Resource resource =
                new FileSystemResource(
                        file.getFilePath());

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + file.getOriginalName()
                                + "\"")
                .body(resource);
    }
    @GetMapping("/doctor")
    public List<FileResponseDto> getFilesForDoctor(
            @RequestParam String doctorEmail,
            @RequestParam String patientEmail) {

        return service.getFilesForDoctor(
                doctorEmail,
                patientEmail);
    }
}