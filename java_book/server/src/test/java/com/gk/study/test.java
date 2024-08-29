package com.gk.study;

import com.gk.study.permission.Access;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class test {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testRedis(){
        stringRedisTemplate.opsForValue().set("key", "value");
    }

    @Test
    void testLength(){
        String str = "b025ab7e52430d82d5e0e5462ebc3909c82a166e3d23cfba82d96472d19fb177";
        System.out.println(str.length());
    }
}
