package com.gk.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gk.study.entity.Application;
import com.gk.study.entity.Borrow;
import com.gk.study.pojo.VO.MyApplicationVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {

    @Select("SELECT * FROM java_book2.b_application where status=#{status} and create_time < #{time}")
    List<Application> getByStatusAndApplicationTimeLT(int status, LocalDateTime time);

    List<MyApplicationVO> selectByUserId(Long userId);
}
