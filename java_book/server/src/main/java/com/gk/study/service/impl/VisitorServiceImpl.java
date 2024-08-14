package com.gk.study.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.entity.User;
import com.gk.study.entity.Visitor;
import com.gk.study.mapper.UserMapper;
import com.gk.study.mapper.VisitorMapper;
import com.gk.study.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {
    @Autowired
    private VisitorMapper visitorMapper;

    /**
     * 根据请求保存UV
     * @param request
     */
    @Override
    public void saveUV(HttpServletRequest request) {
        String uuid = "71883752816819153359652765313139";

        List<Visitor> visitors = visitorMapper.selectByUUID(uuid);
        if(visitors != null && visitors.size() > 0){
            return;
        }

        Visitor visitor = Visitor.builder()
                .uuid(uuid)
                .ip("192.2.2.1")
                .area("无锡")
                .visitTime(LocalDateTime.now())
                .build();
        save(visitor);
    }
}
