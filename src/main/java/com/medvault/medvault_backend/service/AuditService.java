package com.medvault.medvault_backend.service;

import com.medvault.medvault_backend.entity.AuditLog;
import com.medvault.medvault_backend.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void log(
            String email,
            String action) {

        AuditLog log =
                AuditLog.builder()
                        .userEmail(email)
                        .action(action)
                        .build();

        auditLogRepository.save(log);
    }

    public List<AuditLog> getLogs(
            String email) {

        return auditLogRepository
                .findByUserEmail(email);
    }
}