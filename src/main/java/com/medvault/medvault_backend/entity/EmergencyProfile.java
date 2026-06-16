package com.medvault.medvault_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emergency_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User patient;

    private String bloodGroup;

    private String allergies;

    private String conditions;

    private String emergencyContact;
}