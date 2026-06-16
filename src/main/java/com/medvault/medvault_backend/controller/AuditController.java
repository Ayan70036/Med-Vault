package com.medvault.medvault_backend.controller;

import com.medvault.medvault_backend.entity.AuditLog;
import com.medvault.medvault_backend.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/{email}")
    public List<AuditLog> getLogs(
            @PathVariable String email) {

        return auditService.getLogs(email);
    }
}