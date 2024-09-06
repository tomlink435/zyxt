package com.gk.study.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.gk.study.common.APIResponse;
import com.gk.study.common.ResponeCode;
import com.gk.study.entity.User;
import com.gk.study.pojo.DTO.AdminDTO;
import com.gk.study.pojo.DTO.AdminLoginDTO;
import com.gk.study.pojo.VO.AdminLoginVO;
import com.gk.study.pojo.entity.Admin;
import com.gk.study.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 员工登录
     * @param adminLoginDTO
     * @return
     */
    @PostMapping("/login")
    public APIResponse login(@RequestBody AdminLoginDTO adminLoginDTO){
        log.info("员工登录:{}", adminLoginDTO);
        AdminLoginVO loginVO = adminService.login(adminLoginDTO);
        return new APIResponse<>(ResponeCode.SUCCESS, "登录成功", loginVO);
    }

    /**
     * 员工列表
     * @return
     */
    @GetMapping("/list")
    public APIResponse list(){
        List<Admin> list =  adminService.list();
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public APIResponse delete(@PathVariable Long id){
        return new APIResponse();
    }


    /**
     * 启用/禁用用户
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/startOrStop/{status}")
    public APIResponse startOrStop(@PathVariable Integer status, Long id){
        log.info("员工启用/禁用 id:{}, status:{}", id, status);
        adminService.startOrStop(status, id);
        return new APIResponse(ResponeCode.SUCCESS);
    }

    /**
     * 新增用户
     */
    @PostMapping
    public APIResponse add(@RequestBody AdminDTO adminDTO){
        log.info("新增管理员:{}", adminDTO);
        adminService.add(adminDTO);
        return new APIResponse(ResponeCode.SUCCESS);
    }
}
