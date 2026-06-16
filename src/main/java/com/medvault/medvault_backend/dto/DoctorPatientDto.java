package com.medvault.medvault_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorPatientDto {

    private Long patientId;
    private String patientName;
    private String patientEmail;
}