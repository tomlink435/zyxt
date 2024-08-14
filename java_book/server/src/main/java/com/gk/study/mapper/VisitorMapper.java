package com.gk.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gk.study.entity.User;
import com.gk.study.entity.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VisitorMapper extends BaseMapper<Visitor> {

    @Select("select * from b_visitor where uuid=#{uuid};")
    List<Visitor> selectByUUID(String uuid);
}
