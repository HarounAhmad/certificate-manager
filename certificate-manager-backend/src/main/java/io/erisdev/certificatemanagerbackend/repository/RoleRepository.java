package io.erisdev.certificatemanagerbackend.repository;

import io.erisdev.certificatemanagerbackend.model.Role;
import io.erisdev.certificatemanagerbackend.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByType(RoleType type);

}
