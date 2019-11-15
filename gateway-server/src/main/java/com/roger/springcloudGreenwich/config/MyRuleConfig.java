package com.roger.springcloudGreenwich.config;

import com.netflix.loadbalancer.IRule;
import com.roger.springcloudGreenwich.rules.UserServerLoadBalanceRule;
import org.springframework.context.annotation.Bean;

/**
 * Created by admin on 2019/8/27.
 */
//@Configuration
public class MyRuleConfig {
    @Bean
    public IRule myselfRule(){
        return new UserServerLoadBalanceRule();
    }
}
