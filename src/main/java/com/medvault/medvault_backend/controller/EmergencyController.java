package com.medvault.medvault_backend.controller;

import com.medvault.medvault_backend.dto.EmergencyProfileDto;
import com.medvault.medvault_backend.entity.EmergencyProfile;
import com.medvault.medvault_backend.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emergency")
@RequiredArgsConstructor
public class EmergencyController {

    private final EmergencyService emergencyService;

    @PostMapping("/profile")
    public EmergencyProfile saveProfile(
            @RequestBody EmergencyProfileDto dto) {

        return emergencyService
                .saveProfile(dto);
    }

    @GetMapping("/{code}")
    public EmergencyProfile getProfile(
            @PathVariable String code) {

        return emergencyService
                .getProfileByCode(code);
    }

    @GetMapping("/profile/{email}")
    public EmergencyProfile getProfileByEmail(
            @PathVariable String email) {

        return emergencyService
                .getProfileByEmail(email);
    }
}