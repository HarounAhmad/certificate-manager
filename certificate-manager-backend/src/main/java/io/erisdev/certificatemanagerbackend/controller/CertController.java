package io.erisdev.certificatemanagerbackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/secure")
public class CertController
 {

    @PreAuthorize("hasAnyAuthority('SYSADMIN')")
    @GetMapping("/sysadmin-only")
    public String get() {
        return "SysAdmin content";
    }
}
