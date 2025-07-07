package io.erisdev.certificatemanagerbackend.entity;

public enum AuditActionType {
    CREATE,
    UPDATE,
    DELETE,
    LOGIN,
    LOGOUT,
    VIEW,
    EXPORT,
    IMPORT,
    GENERATE_CERTIFICATE,
    REVOKE_CERTIFICATE,
    RENEW_CERTIFICATE,
    UPLOAD_CA_CERTIFICATE,
    DOWNLOAD_CA_CERTIFICATE
}
