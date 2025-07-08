package io.erisdev.certificatemanagerbackend.util;

import io.erisdev.certificatemanagerbackend.dto.AuditLogFilterRequest;
import io.erisdev.certificatemanagerbackend.entity.AuditActionType;
import io.erisdev.certificatemanagerbackend.entity.AuditLog;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AuditLogSpec {

    public static Specification<AuditLog> buildSpecification(Map<String, List<AuditLogFilterRequest.FilterMeta>> filters) {
        Specification<AuditLog> spec = null;

        for (Map.Entry<String, List<AuditLogFilterRequest.FilterMeta>> entry : filters.entrySet()) {
            String field = entry.getKey();
            for (AuditLogFilterRequest.FilterMeta meta : entry.getValue()) {
                Object value = meta.getValue();
                if (value == null) continue;

                Specification<AuditLog> fieldSpec;

                switch (field) {
                    case "id":
                        fieldSpec = parseId(value, field);
                        break;

                    case "timestamp":
                        fieldSpec = parseTimestamp(meta);
                        break;

                    case "actionType":
                        fieldSpec = parseActionType(value);
                        break;

                    default:
                        fieldSpec = parseStringLike(value, field);
                }

                spec = (spec == null) ? fieldSpec : spec.and(fieldSpec);
            }
        }

        return (spec != null) ? spec : (root, query, cb) -> cb.conjunction();
    }

    private static Specification<AuditLog> parseId(Object value, String field) {
        return (root, query, cb) -> {
            try {
                Long idValue = Long.valueOf(value.toString());
                return cb.equal(root.get(field), idValue);
            } catch (NumberFormatException e) {
                return cb.disjunction();
            }
        };
    }

    private static Specification<AuditLog> parseTimestamp(AuditLogFilterRequest.FilterMeta meta) {
        return (root, query, cb) -> {
            Predicate p = cb.conjunction();
            if (meta.getFrom() != null && !meta.getFrom().isEmpty()) {
                Instant from = Instant.parse(meta.getFrom());
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("timestamp"), from));
            }
            if (meta.getTo() != null && !meta.getTo().isEmpty()) {
                Instant to = Instant.parse(meta.getTo());
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("timestamp"), to));
            }
            return p;
        };
    }

    private static Specification<AuditLog> parseActionType(Object value) {
        if (value instanceof Collection<?>) {
            @SuppressWarnings("unchecked")
            Collection<String> rawValues = (Collection<String>) value;

            Collection<AuditActionType> enumValues = rawValues.stream()
                    .map(AuditActionType::valueOf)  // Parse String -> Enum
                    .toList();

            return (root, query, cb) -> root.get("actionType").in(enumValues);
        } else {
            AuditActionType enumValue = AuditActionType.valueOf(value.toString());
            return (root, query, cb) -> cb.equal(root.get("actionType"), enumValue);
        }
    }

    private static Specification<AuditLog> parseStringLike(Object value, String field) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(field).as(String.class)), "%" + value.toString().toLowerCase() + "%");
    }
}
