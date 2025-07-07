package io.erisdev.certificatemanagerbackend.controller;

import io.erisdev.certificatemanagerbackend.dto.UserResponse;
import io.erisdev.certificatemanagerbackend.entity.Role;
import io.erisdev.certificatemanagerbackend.service.UserControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserControllerService userControllerService;


    @PreAuthorize("hasAnyAuthority('SYSADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userControllerService.getAllUsers());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getUserRoleOptions() {
        return ResponseEntity.ok(userControllerService.getRoleOptions());
    }

    @PreAuthorize("hasAuthority('SYSADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userControllerService.deleteUser(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SYSADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserResponse userResponse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserResponse updated = userControllerService.updateUser(id, userResponse,  authentication.getName());
        return ResponseEntity.ok(updated);
    }


}
