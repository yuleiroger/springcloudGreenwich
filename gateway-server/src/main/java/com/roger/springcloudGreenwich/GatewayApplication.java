package com.roger.springcloudGreenwich;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Created by admin on 2019/5/8.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableApolloConfig
@Slf4j
public class GatewayApplication {

    @Value("${deploy.licence}")
    private String licence;

    public static void main(String[] args){
        SpringApplication.run(GatewayApplication.class,args);
    }

    @Bean
    public void setLicenceValue(){
        log.info("licence is====" + licence);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
