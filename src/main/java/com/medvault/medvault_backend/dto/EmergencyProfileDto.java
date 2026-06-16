package com.medvault.medvault_backend.dto;

import lombok.Data;

@Data
public class EmergencyProfileDto {

    private String patientEmail;

    private String bloodGroup;

    private String allergies;

    private String conditions;

    private String emergencyContact;
}