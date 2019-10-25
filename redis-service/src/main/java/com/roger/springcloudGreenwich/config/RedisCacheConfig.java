package com.roger.springcloudGreenwich.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roger.springcloudGreenwich.RedisProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2018/9/29.
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

//    /**
//     * LettuceConnectionFactory 配置
//     *
//     * @return LettuceConnectionFactory
//     */
//    private LettuceConnectionFactory getLettuceConnectionFactory(Integer db) {
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(sentinelConfig(), getLettucePoolingClientConfiguration());
//        lettuceConnectionFactory.setDatabase(db);
//        return lettuceConnectionFactory;
//    }
//
//    /**
//     * lettuce 连接池配置
//     *
//     * @return LettucePoolingClientConfiguration
//     */
//    private LettucePoolingClientConfiguration getLettucePoolingClientConfiguration() {
//        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
//        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
//        genericObjectPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
//        genericObjectPoolConfig.setMinIdle(redisProperties.getMinIdle());
//        builder.poolConfig(genericObjectPoolConfig);
//        builder.commandTimeout(Duration.ofSeconds(60));
//        builder.shutdownTimeout(Duration.ofMillis(100));
//        return builder.build();
//    }
//
//    private RedisSentinelConfiguration sentinelConfig() {
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration();
//        sentinelConfig.setMaster(redisProperties.getSentinelMaster());
//        String[] sentinels = redisProperties.getSentinelNodes().split("\\|");
//        List<RedisNode> list = new ArrayList<>();
//        for (String sentinel : sentinels) {
//            String[] nodes = sentinel.split(":");
//            list.add(new RedisNode(nodes[0], Integer.parseInt(nodes[1])));
//        }
//        sentinelConfig.setSentinels(list);
//        sentinelConfig.setPassword(RedisPassword.of(redisProperties.getSentinelPassword()));
//        return sentinelConfig;
//    }


    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
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
