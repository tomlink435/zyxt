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
    @PostMapping
    public APIResponse post(@RequestBody Application application){
        log.info("保存申请{}", application);
        applicationService.save(application);
        return new APIResponse(ResponeCode.SUCCESS, "保存成功");
    }
    @GetMapping
    public APIResponse test(){
        return new APIResponse(ResponeCode.SUCCESS, "测试成功");
    }
}
