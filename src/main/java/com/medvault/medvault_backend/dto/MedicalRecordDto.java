package com.medvault.medvault_backend.dto;

import lombok.Data;

@Data
public class MedicalRecordDto {

    private String doctorEmail;

    private String patientEmail;

    private String content;
}