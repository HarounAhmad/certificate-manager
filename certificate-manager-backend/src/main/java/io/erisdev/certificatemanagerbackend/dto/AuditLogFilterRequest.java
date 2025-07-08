package io.erisdev.certificatemanagerbackend.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AuditLogFilterRequest {
    private int page;
    private int size;
    private String sortField;
    private int sortOrder;
    private Map<String, List<FilterMeta>> filters;

    @Data
    public static class FilterMeta {
        private Object value;
        private String matchMode;
        private String from;
        private String to;
        private String operator;
    }
}
