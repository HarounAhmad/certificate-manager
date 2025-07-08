package io.erisdev.certificatemanagerbackend.controller;

import io.erisdev.certificatemanagerbackend.dto.AuditLogFilterRequest;
import io.erisdev.certificatemanagerbackend.dto.AuditLogResponse;
import io.erisdev.certificatemanagerbackend.entity.AuditActionType;
import io.erisdev.certificatemanagerbackend.entity.AuditLog;
import io.erisdev.certificatemanagerbackend.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @PreAuthorize("hasAnyAuthority('SYSADMIN')")
    @PostMapping("/get")
    public ResponseEntity<Page<AuditLogResponse>> getAuditLogs(
            @RequestBody AuditLogFilterRequest request
    ) {
        Page<AuditLogResponse> result = auditLogService.getAuditLogs(request);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('SYSADMIN')")
    @GetMapping("/action-types")
    public ResponseEntity<List<AuditActionType>> getAuditActionTypes() {
        return ResponseEntity.ok(auditLogService.getAuditLogTypes());
    }

}
