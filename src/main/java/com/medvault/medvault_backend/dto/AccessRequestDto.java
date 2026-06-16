package com.medvault.medvault_backend.dto;

import lombok.Data;

@Data
public class AccessRequestDto {

    private String doctorEmail;
    private String patientEmail;
    private Integer requestedDays;
}