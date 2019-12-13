package com.roger.springcloudGreenwich;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by admin on 2019/12/12.
 */
@SpringBootApplication
public class ZipkinClient1Application {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinClient1Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
