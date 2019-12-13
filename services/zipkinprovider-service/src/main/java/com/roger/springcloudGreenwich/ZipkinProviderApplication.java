package com.roger.springcloudGreenwich;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by admin on 2019/12/13.
 */
@SpringBootApplication
public class ZipkinProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinProviderApplication.class, args);
    }

}
