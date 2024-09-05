package com.gk.study.service.impl;

import com.gk.study.common.APIResponse;
import com.gk.study.common.ResponeCode;
import com.gk.study.mapper.AdminMapper;
import com.gk.study.pojo.DTO.AdminLoginDTO;
import com.gk.study.pojo.VO.AdminLoginVO;
import com.gk.study.pojo.entity.Admin;
import com.gk.study.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public APIResponse login(AdminLoginDTO adminLoginDTO) {
        String username = adminLoginDTO.getUsername();
        String password = adminLoginDTO.getPassword();
        //1.判断用户名是否存在
        Admin admin = adminMapper.getByUsername(username);
        if(admin == null){
            return new APIResponse(ResponeCode.FAIL, "账号不存在");
        }

        //2.判断密码是否正确
        if(admin.getPassword() != password){
            return new APIResponse(ResponeCode.FAIL, "密码错误");
        }

        //3.判断是否被禁用
        if(admin.getStatus() == 0){
            return new APIResponse(ResponeCode.FAIL, "账号被禁用");
        }

        //存储在ThreadLocal中
        return new APIResponse(ResponeCode.SUCCESS, "登录成功");
    }
}
