package com.roger.springcloudGreenwich.config;

import com.roger.springcloudGreenwich.component.CommonPool2Properties;
import com.roger.springcloudGreenwich.component.SentinelProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by admin on 2019/11/25.
 */
@Configuration
public class RedisClientConfiguration {

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        //todo 定制化配置
        return stringRedisTemplate;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //todo 定制化配置
        return redisTemplate;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisSentinelConfiguration redisSentinelConfiguration, LettuceClientConfiguration lettuceClientConfiguration) {
        return new LettuceConnectionFactory(redisSentinelConfiguration, lettuceClientConfiguration);
    }

    /**
     * 配置哨兵集群信息 master和host:ip
     * @param sentinelProperties  集群Properties
     * @return redisSentinelConfiguration
     */
    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration(SentinelProperties sentinelProperties) {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration(sentinelProperties.getMaster(), sentinelProperties.getHosts());
        redisSentinelConfiguration.setPassword(RedisPassword.of("yulei"));
        return redisSentinelConfiguration;
    }

    /**
     * 配置LettuceClientConfiguration 包括线程池配置和安全项配置
     * @param genericObjectPoolConfig common-pool2线程池
     * @return lettuceClientConfiguration
     */
    @Bean
    public LettuceClientConfiguration lettuceClientConfiguration(GenericObjectPoolConfig genericObjectPoolConfig) {
        return LettucePoolingClientConfiguration.builder()
                .poolConfig(genericObjectPoolConfig)
                .build();
    }

    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig(CommonPool2Properties commonPool2Properties) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(commonPool2Properties.getMaxIdle());
        poolConfig.setMinIdle(commonPool2Properties.getMinIdle());
        poolConfig.setMaxTotal(commonPool2Properties.getMaxTotal());
        //todo 其他配置
        return poolConfig;
    }

    //如果用spring redis session需要这个
//    @Bean
//    public static ConfigureRedisAction configureRedisAction() {
//        return ConfigureRedisAction.NO_OP;
//    }

}

