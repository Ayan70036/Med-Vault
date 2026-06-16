package com.medvault.medvault_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponseDto {

    private Long id;

    private String originalName;

    private String uploadedBy;
}