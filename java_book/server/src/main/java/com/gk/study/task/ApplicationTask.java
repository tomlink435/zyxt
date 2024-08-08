package com.gk.study.task;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gk.study.entity.Application;
import com.gk.study.mapper.ApplicationMapper;
import com.gk.study.utils.UserHolder;
import com.gk.study.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ApplicationTask {
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 自动审核通过
     */
    @Scheduled(cron = "0/15 * * * * ?")
    public void processApplicationPass(){

        LocalDateTime time = LocalDateTime.now().plusSeconds(-15);

        //SELECT * FROM b_application where status=0 and application_time < time

        List<Application> applicationList = applicationMapper.getByStatusAndApplicationTimeLT(0, time);

        if(applicationList != null && applicationList.size() > 0){
            for (Application application : applicationList) {
                log.info("自动审核通过{}",application);
                application.setStatus(1);
                applicationMapper.update(application, new UpdateWrapper<>());

                Map map = new HashMap<>();

                map.put("type", 1);
                map.put("applicationId", application.getId());
                map.put("content", "审核通过啦!");
                String json = JSON.toJSONString(map);

                webSocketServer.sendToAllClient(json);
            }
        }
    }
}
