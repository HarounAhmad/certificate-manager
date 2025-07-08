package io.erisdev.certificatemanagerbackend.service;

import io.erisdev.certificatemanagerbackend.dto.AuditLogFilterRequest;
import io.erisdev.certificatemanagerbackend.dto.AuditLogResponse;
import io.erisdev.certificatemanagerbackend.entity.AuditActionType;
import io.erisdev.certificatemanagerbackend.entity.AuditLog;
import io.erisdev.certificatemanagerbackend.entity.User;
import io.erisdev.certificatemanagerbackend.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.time.Instant;

import static io.erisdev.certificatemanagerbackend.util.AuditLogSpec.*;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;


    public Page<AuditLogResponse> getAuditLogs(AuditLogFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Specification<AuditLog> spec = buildSpecification(request.getFilters());
        return auditLogRepository.findAll(spec, pageable).map(this::mapToResponse);
    }

    public void createAuditLog(String action, String username, Object before, Object after, AuditActionType actionType) {
        String diff = computeObjectDiff(before, after);
        AuditLog log = AuditLog.builder()
                .action(action)
                .actionType(actionType)
                .timestamp(Instant.now())
                .actor(username)
                .affectedUser(before instanceof User user ? user.getUsername() : "N/A")
                .details(diff)
                .build();
        auditLogRepository.save(log);
    }

    private AuditLogResponse mapToResponse(AuditLog auditLog) {
        AuditLogResponse response = new AuditLogResponse();
        response.setId(auditLog.getId());
        response.setAction(auditLog.getAction());
        response.setTimestamp(auditLog.getTimestamp().toString());
        response.setActor(auditLog.getActor());
        response.setDetails(auditLog.getDetails());
        response.setAffectedUser(auditLog.getAffectedUser());
        response.setActionType(auditLog.getActionType().name());
        return response;
    }


    public String computeObjectDiff(Object before, Object after) {
        if (before == null || after == null) return "One of the objects is null";
        if (!before.getClass().equals(after.getClass())) return "Objects are not of same type";

        StringBuilder diff = new StringBuilder();

        // Add context prefix for known types
        if (before instanceof User user) {
            diff.append("User affected: ").append(user.getUsername()).append(" with id: ").append(user.getId()).append("\n");
        }

        Class<?> clazz = before.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object beforeValue = field.get(before);
                Object afterValue = field.get(after);

                if (!Objects.equals(beforeValue, afterValue)) {
                    String beforeString = valueToString(beforeValue);
                    String afterString = valueToString(afterValue);

                    diff.append(field.getName())
                            .append(" changed: ")
                            .append(beforeString)
                            .append(" -> ")
                            .append(afterString)
                            .append("\n");
                }
            } catch (IllegalAccessException e) {
                diff.append("Could not access field: ").append(field.getName()).append("\n");
            }
        }

        if (diff.isEmpty()) {
            diff.append("No changes");
        }

        return diff.toString();
    }

    private String valueToString(Object value) {
        if (value instanceof Collection) {
            return ((Collection<?>) value).stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ", "[", "]"));
        }
        return String.valueOf(value);
    }


    public List<AuditActionType> getAuditLogTypes() {
        return List.of(AuditActionType.values());
    }
}
