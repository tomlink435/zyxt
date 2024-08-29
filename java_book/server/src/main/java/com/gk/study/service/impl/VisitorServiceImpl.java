package com.gk.study.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.entity.User;
import com.gk.study.entity.Visitor;
import com.gk.study.mapper.UserMapper;
import com.gk.study.mapper.VisitorMapper;
import com.gk.study.pojo.DTO.UserDTO;
import com.gk.study.service.VisitorService;
import com.gk.study.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {
    @Autowired
    private VisitorMapper visitorMapper;

    private static Object lock = new Object();

    /**
     * 保存ip
     * @param ip
     */
    @Override
    public void saveIP(String ip) {
        LocalDateTime time = LocalDateTime.now().plusMinutes(-10L);

        synchronized (lock){
            log.info("时间:{}", time);
            Integer count = visitorMapper.countByIpAndGT(ip, time);
            if(count > 0){
                //已经存在不保存
                return;
            }
            UserDTO user = UserHolder.getUser();
            Long id = 0L;
            if(user != null){
                id = Long.parseLong(user.getId());
                log.info("用户id:{}",id);
            }else{
                log.info("访客");
            }

            Visitor visitor = Visitor.builder()
                    .ip(ip)
                    .userId(id)
                    .visitTime(LocalDateTime.now())
                    .build();
            log.info("保存访客:{}", visitor);
            save(visitor);
        }
    }
}
