package io.erisdev.certificatemanagerbackend.repository;

import io.erisdev.certificatemanagerbackend.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
