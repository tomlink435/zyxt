package com.gk.study.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.gk.study.common.APIResponse;
import com.gk.study.common.BaseException;
import com.gk.study.common.ResponeCode;
import com.gk.study.entity.Ad;
import com.gk.study.mapper.AdminMapper;
import com.gk.study.pojo.DTO.AdminDTO;
import com.gk.study.pojo.DTO.AdminLoginDTO;
import com.gk.study.pojo.DTO.UserDTO;
import com.gk.study.pojo.VO.AdminLoginVO;
import com.gk.study.pojo.entity.Admin;
import com.gk.study.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public AdminLoginVO login(AdminLoginDTO adminLoginDTO) {
        String username = adminLoginDTO.getUsername();
        String password = adminLoginDTO.getPassword();
        //1.判断用户名是否存在
        Admin admin = adminMapper.getByUsername(username);
        if(admin == null){
            throw new BaseException("用户名不存在");
        }

        //密码比对
        //对前端传过来的明文密码进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!password.equals(admin.getPassword())){
            throw new BaseException("密码错误");
        }
        if(admin.getStatus() == 0){
            //账号被禁用
            throw new BaseException("账号被禁用");
        }

        AdminLoginVO adminLoginVO = BeanUtil.copyProperties(admin, AdminLoginVO.class);

        return adminLoginVO;
    }

    /**
     * 员工 启用\禁用
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Admin admin = new Admin();
        admin.setId(id);
        admin.setStatus(status);
        adminMapper.update(admin);
    }

    /**
     * 新增管理员
     * @param adminDTO
     * @return
     */
    @Override
    public void add(AdminDTO adminDTO) {
        Admin admin = adminMapper.getByUsername(adminDTO.getUsername());
        if(admin != null){
            throw new BaseException("用户名已存在");
        }
        //密码判断
        if(!adminDTO.getPassword().equals(adminDTO.getRePassword())){
            throw new BaseException("两次密码输入不一致，请重新输入");
        }

        //插入成功
        admin = BeanUtil.copyProperties(adminDTO, Admin.class);
        admin.setCreateTime(LocalDateTime.now());
        admin.setPassword(DigestUtils.md5DigestAsHex(adminDTO.getRePassword().getBytes()));
        adminMapper.insert(admin);
    }

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }


}
