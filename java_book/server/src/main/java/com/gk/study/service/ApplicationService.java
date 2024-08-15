package com.gk.study.service;

import com.gk.study.common.APIResponse;
import com.gk.study.entity.Application;
import com.gk.study.pojo.DTO.ApplicationDTO;
import com.gk.study.pojo.VO.MyApplicationVO;

import java.util.List;

public interface ApplicationService {

    APIResponse save(ApplicationDTO applicationDTO);

    List<Application> list();

    void pass(Long id);

    void reject(Long id);

    List<Application> query(ApplicationDTO applicationDTO);

    List<MyApplicationVO> getByUserId(Long userId);
}
