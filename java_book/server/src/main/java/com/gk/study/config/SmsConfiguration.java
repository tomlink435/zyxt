package com.gk.study.config;

import com.aliyun.teaopenapi.Client;
import com.gk.study.properties.SmsProperties;
import com.gk.study.utils.SendSmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SmsConfiguration {
    @Bean
    public SendSmsUtils sendSmsUtils(SmsProperties smsProperties){
        log.info("开始创建阿里云短信发送工具类对象:{}", smsProperties);
        return new SendSmsUtils(smsProperties.getEndpoint(),
                smsProperties.getAccessKeyId(),
                smsProperties.getAccessKeySecret());
    }
}
