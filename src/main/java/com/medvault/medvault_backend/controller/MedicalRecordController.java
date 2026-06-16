package com.medvault.medvault_backend.controller;

import com.medvault.medvault_backend.dto.MedicalRecordDto;
import com.medvault.medvault_backend.dto.RecordResponseDto;
import com.medvault.medvault_backend.entity.MedicalRecord;
import com.medvault.medvault_backend.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService service;

    @PostMapping
    public MedicalRecord createRecord(
            @RequestBody MedicalRecordDto dto) {

        return service.createRecord(dto);
    }

    @GetMapping("/patient")
    public List<RecordResponseDto> getPatientRecords(
            @RequestParam String doctorEmail,
            @RequestParam String patientEmail) {

        return service.getDecryptedRecords(
                doctorEmail,
                patientEmail);
    }
    @GetMapping("/my/{email}")
    public List<RecordResponseDto> getMyRecords(
            @PathVariable String email) {

        return service.getMyDecryptedRecords(email);
    }
}