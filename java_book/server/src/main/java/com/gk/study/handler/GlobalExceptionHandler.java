package com.gk.study.handler;

import com.gk.study.common.APIResponse;
import com.gk.study.common.BaseException;
import com.gk.study.common.ResponeCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，处理项目中抛出的异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public APIResponse exceptionHandler(BaseException e){
        log.info("业务异常:{}", e);
        return new APIResponse(ResponeCode.FAIL, e.getMessage());
    }
}
