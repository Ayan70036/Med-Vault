package com.medvault.medvault_backend.controller;

import com.medvault.medvault_backend.dto.AccessRequestDto;
import com.medvault.medvault_backend.dto.DoctorPatientDto;
import com.medvault.medvault_backend.entity.AccessRequest;
import com.medvault.medvault_backend.service.AccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/access")
@RequiredArgsConstructor
public class AccessController {

    private final AccessService accessService;

    @PostMapping("/request")
    public AccessRequest requestAccess(
            @RequestBody AccessRequestDto dto) {

        return accessService.createRequest(dto);
    }

    @GetMapping("/patient/{email}")
    public List<AccessRequest> getPatientRequests(
            @PathVariable String email) {

        return accessService.getPatientRequests(email);
    }

    @PutMapping("/approve/{id}")
    public String approveRequest(
            @PathVariable Long id) {

        return accessService.approveRequest(id);
    }

    @PutMapping("/reject/{id}")
    public String rejectRequest(
            @PathVariable Long id) {

        return accessService.rejectRequest(id);
    }



    @GetMapping("/doctor/{email}")
    public List<DoctorPatientDto> getDoctorPatients(
            @PathVariable String email) {

        return accessService
                .getDoctorPatients(email);
    }
}