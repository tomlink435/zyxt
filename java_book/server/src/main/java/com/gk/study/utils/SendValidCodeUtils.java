package com.gk.study.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 发送验证码
 */
@Slf4j
public class SendValidCodeUtils {

    /**
     * 向phone发送验证码code
     * @param phone
     * @param code
     */
    public void sendValidCodeUtils(String phone, String code){
        log.info("向" + phone + "发送了验证码" + code);
    }
}
