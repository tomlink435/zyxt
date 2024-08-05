package com.gk.study.task;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gk.study.entity.Application;
import com.gk.study.mapper.ApplicationMapper;
import com.gk.study.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class ApplicationTask {

    @Autowired
    private ApplicationMapper applicationMapper;

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
            }
        }
    }
}
