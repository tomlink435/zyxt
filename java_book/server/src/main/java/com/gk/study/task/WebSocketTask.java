package com.gk.study.task;

import com.alibaba.fastjson.JSON;
import com.gk.study.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketTask {
    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 通过WebSocket每隔5秒向客户端发送消息
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void sendMessageToClient() {
        Map map = new HashMap<>();

        map.put("type", 1);
        map.put("content", "审核通过啦!");
        String json = JSON.toJSONString(map);

        webSocketServer.sendToAllClient(json);
    }
}
