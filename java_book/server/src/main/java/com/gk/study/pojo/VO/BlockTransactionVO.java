package com.gk.study.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockTransactionVO {
    private Metadata metadata;
    private Spec spec;
    private Status status;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metadata {
        private String name;
        private String namespace;
        private String uid;
        private String resourceVersion;
        private int generation;
        private String creationTimestamp;
        private List<ManagedFields> managedFields;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManagedFields {
        private String manager;
        private String operation;
        private String apiVersion;
        private String time;
        private String fieldsType;
        private FieldsV1 fieldsV1;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldsV1 {
        private Map<String, Object> spec;
        private Map<String, Object> status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Spec {
        private String id;
        private String status;
        private int no;
        private int blockNum;
        private String chaincodeId;
        private String chaincodeVersion;
        private String mspId;
        private List<String> payload;
        private List<String> evidenceFile;
        private List<String> fileType;
        private int type;
        private String channel;
        private String timeStamp;
        private String user;
        private List<String> encryptKeys;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Status {
        private String create_time;
    }
}
