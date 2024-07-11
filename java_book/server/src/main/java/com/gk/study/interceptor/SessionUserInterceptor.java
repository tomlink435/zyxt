package com.gk.study.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Session校验登录的拦截器
 */
@Component
@Slf4j
public class SessionUserInterceptor implements HandlerInterceptor {

    public boolean preHandler(HttpServletRequest request, HttpServletResponse response){
        //TODO:判断当前拦截的是否是需要拦截的方法

        //1.从Session中获取user_id
        request.getSession().getAttribute("user_id");
        //2.校验user_id
            //校验成功，放入ThreadLocal

            //不通过，响应401

        return true;
    }
}
