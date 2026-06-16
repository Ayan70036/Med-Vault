package com.medvault.medvault_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User doctor;

    @ManyToOne
    private User patient;

    private Integer requestedDays;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}