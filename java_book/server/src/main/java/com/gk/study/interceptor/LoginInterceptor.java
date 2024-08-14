package com.gk.study.interceptor;

import cn.hutool.core.util.StrUtil;
import com.gk.study.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;

    public LoginInterceptor(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {//1.获取请求头中的token
        //1.判断是否需要拦截(ThreadLocal中是否有用户)
        String url = request.getServletPath();
        String token = request.getHeader("authorization");
        if(UserHolder.getUser() == null){
            log.info("请求:{}, token:{}, 被Login拦截器拦截", url, token);
//            response.setStatus(401);
            return false;
        }
        return true;
    }
}
