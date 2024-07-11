package com.gk.study.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
    private Long id;
    private String username;
    private String password;
    private String rePassword;
    private String nickname;
    private String mobile;
    private String email;
    private String description;
    private String role;
    private String status;
    private String score;
    private String avatar;
    private MultipartFile avatarFile;
    private String token;
    private String createTime;
    private String pushEmail;
    private String pushSwitch;
}
