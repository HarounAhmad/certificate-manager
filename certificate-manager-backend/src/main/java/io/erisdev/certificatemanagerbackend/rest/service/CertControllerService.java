package io.erisdev.certificatemanagerbackend.rest.service;

import io.erisdev.certificatemanagerbackend.model.AuditLog;
import io.erisdev.certificatemanagerbackend.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertControllerService {


    @Autowired
    private AuditLogRepository auditLogRepository;

    public Boolean uploadCertificate(String certData, String username, String ipAddress) {
        // Logic to handle certificate upload
        // Save audit log entry
        auditLogRepository.save(
                AuditLog.builder()
                        .action("UPLOAD_CERTIFICATE")
                        .username(username)
                        .ipAddress(ipAddress)
                        .details("Certificate uploaded successfully")
                        .build()
        );

        // Return appropriate response
        return true;
    }


}
