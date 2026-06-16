package com.medvault.medvault_backend.dto;

import com.medvault.medvault_backend.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private Role role;
}