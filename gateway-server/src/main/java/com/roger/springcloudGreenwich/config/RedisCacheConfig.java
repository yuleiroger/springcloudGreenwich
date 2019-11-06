package com.roger.springcloudGreenwich.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by admin on 2018/9/29.
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisPropertiesConfig redisProperties;

    @Autowired
    private LettucePoolingClientConfiguration lettuceClientConfiguration;
    @Autowired
    private RedisSentinelConfiguration redisSentinelConfiguration;
    @Autowired
    private RedisStandaloneConfiguration redisStandaloneConfiguration;

    /**
     * redis哨兵配置
     *
     * @return
     */
    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration();
        sentinelConfig.setMaster(redisProperties.getMaster());
        Set<RedisNode> sentinels = new HashSet();
        String[] host = redisProperties.getRedisNodes().split(",");
        for (String redisHost : host) {
            String[] item = redisHost.split(":");
            String ip = item[0].trim();
            String port = item[1].trim();
            sentinels.add(new RedisNode(ip, Integer.parseInt(port)));
        }
        sentinelConfig.setSentinels(sentinels);
        sentinelConfig.setDatabase(redisProperties.getDatabase());
        sentinelConfig.setPassword(RedisPassword.of(redisProperties.getPassword())); //redis 密码
        return sentinelConfig;
    }

    /**
     * redis 单节点配置
     *
     * @return
     */
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration standConfig = new RedisStandaloneConfiguration();
        standConfig.setHostName(redisProperties.getHost());
        standConfig.setPort(redisProperties.getPort());
        standConfig.setDatabase(redisProperties.getDatabase());
        standConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));  //redis 密码
        return standConfig;
    }

    /**
     * lettuce 连接池配置
     *
     * @return
     */
    @Bean
    public LettucePoolingClientConfiguration lettucePoolConfig() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

        poolConfig.setMaxTotal(redisProperties.getMaxActive());
        poolConfig.setMinIdle(redisProperties.getMinIdle());
        poolConfig.setMaxIdle(redisProperties.getMaxIdle());
        poolConfig.setMaxWaitMillis(redisProperties.getMaxWait());
        poolConfig.setTestOnCreate(redisProperties.getTestOnCreate());
        poolConfig.setTestOnBorrow(redisProperties.getTestOnBorrow());
        poolConfig.setTestOnReturn(redisProperties.getTestOnReturn());
        poolConfig.setTestWhileIdle(redisProperties.getTestWhileIdle());
        poolConfig.setNumTestsPerEvictionRun(redisProperties.getNumTestsPerEvictionRun());
        poolConfig.setTimeBetweenEvictionRunsMillis(redisProperties.getTimeBetweenEvictionRunsMillis());
        poolConfig.setMinEvictableIdleTimeMillis(redisProperties.getMinEvictableIdleTimeMills());

        return LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .commandTimeout(Duration.ofSeconds(redisProperties.getCommandTimeout()))
                .shutdownTimeout(Duration.ofMillis(redisProperties.getShutdownTimeout()))
                .build();
    }

    /**
     * lettuce 连接工厂
     *
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory factory;
        if (redisProperties.getCluster()) {
            factory = new LettuceConnectionFactory(redisSentinelConfiguration, lettuceClientConfiguration);
        } else {
            factory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
        }
        return factory;
    }


    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        this.setSerializer(redisTemplate);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    //序列化定义
    private void setSerializer(RedisTemplate redisTemplate){
        @SuppressWarnings({ "rawtypes", "unchecked" })
        // 设置序列化
         Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);// key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化
    }

}
