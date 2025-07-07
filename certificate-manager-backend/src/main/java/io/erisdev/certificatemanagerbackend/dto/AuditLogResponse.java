package io.erisdev.certificatemanagerbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogResponse {
    private Long id;
    private String action;
    private String actor;
    private String timestamp;
    private String details;
    private String actionType;
    private String affectedUser;
}
