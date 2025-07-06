package io.erisdev.certificatemanagerbackend.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/certificates")
public class CertController {

    @PostMapping("/certs/upload")
    public ResponseEntity<?> uploadCert() {
        return ResponseEntity.ok().build();
    }
}
