package com.gk.study.service;

import com.gk.study.common.APIResponse;
import com.gk.study.pojo.DTO.AdminDTO;
import com.gk.study.pojo.DTO.AdminLoginDTO;
import com.gk.study.pojo.VO.AdminLoginVO;

public interface AdminService {
    AdminLoginVO login(AdminLoginDTO adminLoginDTO);

    void startOrStop(Integer status, Long id);

    void add(AdminDTO adminDTO);
}
