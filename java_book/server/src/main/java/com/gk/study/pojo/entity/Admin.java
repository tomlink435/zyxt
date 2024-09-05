package com.gk.study.pojo.entity;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private Integer role;
    private LocalDateTime createTime;

}
