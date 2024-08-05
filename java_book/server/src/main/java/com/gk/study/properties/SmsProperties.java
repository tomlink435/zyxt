package com.gk.study.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "zyxt.sms")
@Data
public class SmsProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
}
