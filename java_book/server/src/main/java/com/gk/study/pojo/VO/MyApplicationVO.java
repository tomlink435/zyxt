package com.gk.study.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyApplicationVO {
    private String cover;
    private String title;
    private String description;
    private Integer status;
    private String name;
    private String purpose;
    private String projectName;
    private Long thingId;
}
