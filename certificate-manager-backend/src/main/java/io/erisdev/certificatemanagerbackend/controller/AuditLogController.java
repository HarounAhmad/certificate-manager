package io.erisdev.certificatemanagerbackend.controller;

import io.erisdev.certificatemanagerbackend.dto.AuditLogResponse;
import io.erisdev.certificatemanagerbackend.entity.AuditLog;
import io.erisdev.certificatemanagerbackend.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;


    @GetMapping("/get")
    public ResponseEntity<Page<AuditLogResponse>> getAuditLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(auditLogService.getAuditLogs(page, size));
    }}
