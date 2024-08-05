package com.gk.study.handler;


import com.gk.study.common.APIResponse;
import com.gk.study.common.BaseException;
import com.gk.study.service.ErrorLogService;
import com.gk.study.common.ResponeCode;
import com.gk.study.entity.ErrorLog;
import com.gk.study.utils.HttpContextUtils;
import com.gk.study.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public APIResponse exceptionHandler(Exception ex){
        log.error("异常信息：{}", ex.getMessage());
        return new APIResponse(ResponeCode.FAIL,ex.getMessage());
    }


    /**
     * 处理SQL异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public APIResponse exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //Duplicate entry '冷锋' for key 'employee.idx_username'
        String message = ex.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + "已存在";
            return new APIResponse(ResponeCode.FAIL, msg);
        }else{
            return new APIResponse(ResponeCode.FAIL, "未知错误");
        }
    }
}
