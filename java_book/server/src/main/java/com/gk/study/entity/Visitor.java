package com.gk.study.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("b_visitor")
@Builder
public class Visitor {
    private Long id;
    private String ip;
    private String area;
    private Long userId;
    private LocalDateTime visitTime;
}
