package com.gk.study.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {
    private String name;
    private String email;
    private String phone;
    private String company;
    private String purpose;
    private String projectName;
    private String projectLeader;
    private String projectType;
    private Long userId;
    private Long thingId;
}
