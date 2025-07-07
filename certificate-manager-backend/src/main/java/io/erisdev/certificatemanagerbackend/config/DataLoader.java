package io.erisdev.certificatemanagerbackend.config;

import io.erisdev.certificatemanagerbackend.entity.Role;
import io.erisdev.certificatemanagerbackend.entity.User;
import io.erisdev.certificatemanagerbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("sysadmin").isEmpty()) {
            User sysadmin = new User();
            sysadmin.setUsername("sysadmin");
            sysadmin.setPassword(passwordEncoder.encode("password"));
            sysadmin.setRoles(Set.of(Role.SYSADMIN));
            userRepository.save(sysadmin);
        }

        if (userRepository.findByUsername("auditor").isEmpty()) {
            User auditor = new User();
            auditor.setUsername("auditor");
            auditor.setPassword(passwordEncoder.encode("password"));
            auditor.setRoles(Set.of(Role.AUDITOR));
            userRepository.save(auditor);
        }

        if (userRepository.findByUsername("certadmin").isEmpty()) {
            User certAdmin = new User();
            certAdmin.setUsername("certadmin");
            certAdmin.setPassword(passwordEncoder.encode("password"));
            certAdmin.setRoles(Set.of(Role.CERT_ADMIN));
            userRepository.save(certAdmin);
        }
    }

}
