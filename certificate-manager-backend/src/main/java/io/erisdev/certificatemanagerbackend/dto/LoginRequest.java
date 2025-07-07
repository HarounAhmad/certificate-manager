package io.erisdev.certificatemanagerbackend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}