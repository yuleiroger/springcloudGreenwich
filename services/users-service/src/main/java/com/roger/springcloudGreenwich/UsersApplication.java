package com.roger.springcloudGreenwich;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by admin on 2019/10/21.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableApolloConfig
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60*30)
public class UsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }
}
