package io.erisdev.certificatemanagerbackend;

import io.erisdev.certificatemanagerbackend.model.Role;
import io.erisdev.certificatemanagerbackend.model.RoleType;
import io.erisdev.certificatemanagerbackend.model.User;
import io.erisdev.certificatemanagerbackend.repository.RoleRepository;
import io.erisdev.certificatemanagerbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class CertificateManagerBackendApplication {

     @Autowired
    BCryptPasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(CertificateManagerBackendApplication.class, args);
    }


    @Bean
    CommandLineRunner init(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder encoder) {
        return args -> {
            // Create roles if missing
            for (RoleType rt : RoleType.values()) {
                if (roleRepository.findByType(rt).isEmpty()) {
                    roleRepository.save(new Role(null, rt));
                }
            }

            // Create default superadmin if missing
            if (userRepository.findByUsername("admin").isEmpty()) {
                Role superAdminRole = roleRepository.findByType(RoleType.SUPERADMIN).orElseThrow();
                User admin = User.builder()
                        .username("admin")
                        .password(encoder.encode("admin"))
                        .roles(Set.of(superAdminRole))
                        .build();
                userRepository.save(admin);
            }
        };
    }

}
