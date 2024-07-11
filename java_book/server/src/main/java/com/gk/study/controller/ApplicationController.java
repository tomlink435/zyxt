package com.gk.study.controller;

import com.gk.study.common.APIResponse;
import com.gk.study.common.ResponeCode;
import com.gk.study.entity.Application;
import com.gk.study.entity.Result;
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
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    /**
     * 保存表单
     * @param application
     * @return
     */
    @PostMapping(value = "/save")
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
     * 对表单进行审核
     * 表单状态: 0 未审核, 1 审核通过, 2 审核未通过
     */

    /**
     * 审核通过
     */
    @PutMapping(value = "/pass/{id}")
    public APIResponse pass(@PathVariable Long id){
        log.info("审核通过application_id{}", id);
        applicationService.pass(id);
        return new APIResponse(ResponeCode.SUCCESS);
    }

    /**
     * 审核拒绝
     */
    @PutMapping(value = "/reject/{id}/")
    public APIResponse reject(@PathVariable Long id){
        log.info("审核拒绝application_id{}", id);
        applicationService.reject(id);
        return new APIResponse(ResponeCode.SUCCESS);
    }

}
