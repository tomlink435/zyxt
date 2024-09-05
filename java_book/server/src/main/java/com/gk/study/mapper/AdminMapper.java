package com.gk.study.mapper;

import com.gk.study.pojo.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM admin WHERE username=#{username};")
    Admin getByUsername(String username);
}
