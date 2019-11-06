package com.roger.springcloudGreenwich.component;

import com.roger.springcloudGreenwich.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2019/11/6.
 */
@Component
public class UtilComponent {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Bean
    public RedisUtil gatewayRedisUtil(){
        return new RedisUtil(redisTemplate);
    }
}
