package com.gk.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gk.study.entity.User;
import com.gk.study.entity.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface VisitorMapper extends BaseMapper<Visitor> {

    @Select("SELECT COUNT(*) count FROM b_visitor WHERE ip=#{ip} AND b_visitor.visit_time >= #{time}")
    Integer countByIpAndGT(String ip, LocalDateTime time);

}
