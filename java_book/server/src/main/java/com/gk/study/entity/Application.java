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
    @TableField
    public String address;
    @TableField
    public String description;
    @TableField
    public String project;
    @TableField
    public String courseName;
    @TableField
    public Long classificationId;
    @TableField
    public String status;


}
