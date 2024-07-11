package com.gk.study.interceptor;

import com.gk.study.common.BaseContext;
import com.gk.study.common.JwtProperties;
import com.gk.study.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt令牌校验拦截器
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验Jwt令牌
     * @param request
     * @param response
     * @return
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if(!(handler instanceof HandlerMethod)){
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        try{
            //1.从请求头中获取令牌
            String token = request.getHeader(jwtProperties.getUserTokenName());

            //2.校验令牌
            log.info("jwt校验：{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = Long.valueOf(claims.get("userId").toString());

            log.info("当前用户id:{}", userId);
            BaseContext.setCurrentId(userId);

            //3.通过放行
            return true;
        }catch (Exception ex){
            //4.不通过,响应401
            response.setStatus(401);
            return false;
        }
    }
}
