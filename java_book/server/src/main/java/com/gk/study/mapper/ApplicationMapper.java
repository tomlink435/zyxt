package com.gk.study.mapper;

import com.gk.study.entity.Application;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationMapper {
    void insert(Application application);
}
