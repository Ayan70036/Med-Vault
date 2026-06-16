package com.medvault.medvault_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecordResponseDto {

    private String doctorName;

    private String content;

    private LocalDateTime createdAt;
}