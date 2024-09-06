package com.gk.study.mapper;

import com.gk.study.pojo.entity.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM admin WHERE username=#{username};")
    Admin getByUsername(String username);
    void update(Admin admin);

    @Insert("INSERT INTO admin(username, password, status, role, create_time)" +
            "VALUES (#{username}, #{password}, #{status}, #{role}, #{createTime});")
    void insert(Admin admin);
}
