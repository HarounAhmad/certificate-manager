package io.erisdev.certificatemanagerbackend.rest;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/whoami")
    public String whoami(Authentication authentication) {
        return "Authenticated as: " + authentication.getName();
    }

    @PostMapping
    public String login() {
        // This endpoint is just a placeholder for login.
        // Actual authentication is handled by Spring Security.
        return "Login endpoint - not implemented";
    }

}
