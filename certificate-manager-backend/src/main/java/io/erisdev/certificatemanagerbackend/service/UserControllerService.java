package io.erisdev.certificatemanagerbackend.service;


import io.erisdev.certificatemanagerbackend.dto.UserResponse;
import io.erisdev.certificatemanagerbackend.entity.AuditActionType;
import io.erisdev.certificatemanagerbackend.entity.AuditLog;
import io.erisdev.certificatemanagerbackend.entity.Role;
import io.erisdev.certificatemanagerbackend.entity.User;
import io.erisdev.certificatemanagerbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserControllerService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuditLogService auditLogService;

    public UserControllerService(UserRepository userRepository, AuditLogService auditLogService) {
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        if (users.isEmpty()) {
            return List.of(); // Return an empty list if no users are found
        }

        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles() != null ? user.getRoles().stream().map(Role::toString).toList() : List.of())
                .build();
    }

    public List<Role> getRoleOptions() {
        return List.of(Role.values());
    }

    public void deleteUser(Long id, String username) {
        this.auditLogService.createAuditLog("User deletion initiated for user ID: " + id, username, null, null, AuditActionType.DELETE);
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        this.userRepository.delete(user);
    }

    public UserResponse updateUser(Long id, UserResponse userResponse, String username) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        User preUpdatedUser = User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(new HashSet<>(user.getRoles()))
                .build();
        user.setUsername(userResponse.getUsername());
        if (userResponse.getRoles() != null) {
            Set<Role> roles = userResponse.getRoles().stream()
                    .map(Role::valueOf)
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        User updatedUser = this.userRepository.save(user);
        this.auditLogService.createAuditLog("User update initiated for user ID: " + id, username, preUpdatedUser, updatedUser, AuditActionType.UPDATE);
        return mapToUserResponse(updatedUser);
    }
}
