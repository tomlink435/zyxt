package com.gk.study.interceptor;

import com.gk.study.service.VisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
public class UVInterceptor implements HandlerInterceptor {

    private VisitorService visitorService;

    public UVInterceptor(VisitorService visitorService){
        this.visitorService = visitorService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        log.info("用户ip:{}", ip);
        visitorService.saveIP(ip);
        return true;
    }
}
