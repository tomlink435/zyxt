package com.gk.study.controller;

import com.gk.study.common.APIResponse;
import com.gk.study.common.ResponeCode;
import com.gk.study.entity.Application;
import com.gk.study.entity.Result;
import com.gk.study.permission.Access;
import com.gk.study.permission.AccessLevel;
import com.gk.study.pojo.DTO.ApplicationDTO;
import com.gk.study.service.ApplicationService;
import javassist.tools.rmi.AppletServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 提交申请
 */
@Slf4j
@RestController
@RequestMapping("/application")
@CrossOrigin
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    /**
     * 保存表单
     * @param application
     * @return
     */
    @PostMapping(value = "/save/")
    public APIResponse post(@RequestBody Application application){
        log.info("保存申请{}", application);
        applicationService.save(application);
        return new APIResponse(ResponeCode.SUCCESS, "保存成功");
    }

    /**
     * 查询表单
     * @return
     */
    @GetMapping(value = "/list")
    public APIResponse list(){
        List<Application> applicationList = applicationService.list();
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", applicationList);
    }

    /**
     * 查询审核
     * @param applicationDTO
     * @return
     */
    @GetMapping(value = "/query")
    public APIResponse query(ApplicationDTO applicationDTO){
        log.info("审核查询{}", applicationDTO);
        List<Application> applicationList = applicationService.query(applicationDTO);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", applicationList);
    }

    /**
     * 审核通过
     * @param id
     * @return
     */

    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public APIResponse pass(Long id){
        applicationService.pass(id);
        return new APIResponse(ResponeCode.SUCCESS);

    }

    /**
     * 审核不通过
     * @param id
     * @return
     */
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public APIResponse reject(Long id){
        applicationService.reject(id);
        return new APIResponse(ResponeCode.SUCCESS);
    }
}
