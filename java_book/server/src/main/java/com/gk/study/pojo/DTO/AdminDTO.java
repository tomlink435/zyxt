package com.gk.study.pojo.DTO;

import lombok.Data;

@Data
public class AdminDTO {
    private String username;
    private String password;
    private String rePassword;
    private Integer status;
    private Integer role;
}
