package com.gk.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("b_application")
public class Application implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    public Long id;
    @TableField
    public String name;
    @TableField
    public String email;
    public String phone;
    @TableField
    public String address;
    @TableField
    public String description;
    @TableField
    public String company;
    @TableField
    public String purpose;
    @TableField
    public String projectLeader;
    @TableField
    public String projectType;
    @TableField
    public String status;
}
