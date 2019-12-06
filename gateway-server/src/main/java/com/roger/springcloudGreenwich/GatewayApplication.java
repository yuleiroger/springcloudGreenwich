package com.roger.springcloudGreenwich;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by admin on 2019/5/8.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableApolloConfig
public class GatewayApplication {

    @Value("${deploy.licence}")
    private String licence;

    public static void main(String[] args){

        SpringApplication.run(GatewayApplication.class,args);
    }

}
