package com.roger.springcloudGreenwich.config;

import com.netflix.loadbalancer.IRule;
import com.roger.springcloudGreenwich.rules.CustomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by admin on 2019/8/27.
 */
@Configuration
public class MyRuleConfig {
    @Bean
    public IRule myselfRule(){
        return new CustomRule();
    }
}
