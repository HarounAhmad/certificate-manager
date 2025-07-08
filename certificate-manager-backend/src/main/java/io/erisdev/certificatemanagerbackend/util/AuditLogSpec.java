package io.erisdev.certificatemanagerbackend.util;

import io.erisdev.certificatemanagerbackend.dto.AuditLogFilterRequest;
import io.erisdev.certificatemanagerbackend.entity.AuditLog;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class AuditLogSpec {

    public static Specification<AuditLog> buildSpecification(Map<String, List<AuditLogFilterRequest.FilterMeta>> filters) {
        Specification<AuditLog> spec = null;

        for (Map.Entry<String, List<AuditLogFilterRequest.FilterMeta>> entry : filters.entrySet()) {
            String field = entry.getKey();
            for (AuditLogFilterRequest.FilterMeta meta : entry.getValue()) {
                if (meta.getValue() != null && !meta.getValue().isEmpty()) {
                    Specification<AuditLog> fieldSpec;

                    if ("id".equals(field)) {
                        fieldSpec = (root, query, cb) -> {
                            try {
                                Long idValue = Long.valueOf(meta.getValue());
                                return cb.equal(root.get(field), idValue);
                            } catch (NumberFormatException e) {
                                return cb.disjunction(); // skip invalid id
                            }
                        };
                    } else if ("timestamp".equals(field)) {
                        fieldSpec = (root, query, cb) -> {
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
                    } else {
                        fieldSpec = (root, query, cb) ->
                                cb.like(cb.lower(root.get(field).as(String.class)), "%" + meta.getValue().toLowerCase() + "%");
                    }

                    spec = (spec == null) ? fieldSpec : spec.and(fieldSpec);
                }
            }
        }

        return (spec != null) ? spec : (root, query, cb) -> cb.conjunction();
    }

}
