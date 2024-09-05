package com.gk.study.controller.admin;

import com.gk.study.common.APIResponse;
import com.gk.study.common.ResponeCode;
import com.gk.study.pojo.DTO.AdminLoginDTO;
import com.gk.study.pojo.VO.AdminLoginVO;
import com.gk.study.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/")
@Slf4j
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
        return adminService.login(adminLoginDTO);
    }

    /**
     * 员工列表
     * @return
     */
    @GetMapping("/list")
    public APIResponse list(){
        return new APIResponse();
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


    @PostMapping("/startOrStop/{status}")
    public APIResponse startOrStop(@PathVariable Integer status, Long id){
        return new APIResponse();
    }






}
