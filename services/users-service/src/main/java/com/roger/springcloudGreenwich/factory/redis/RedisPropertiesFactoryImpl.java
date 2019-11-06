package com.roger.springcloudGreenwich.factory.redis;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.roger.springcloudGreenwich.RedisProperties;

/**
 * Created by yulei on 2019/11/6.
 */
public class RedisPropertiesFactoryImpl extends RedisPropertiesFactory{
    @Override
    public RedisProperties generateRedisProperties() {
        Config config = ConfigService.getAppConfig();
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setRedisNodes(config.getProperty("spring.redis.sentinel.nodes",""));
        redisProperties.setMaster(config.getProperty("spring.redis.sentinel.master",""));
        redisProperties.setDatabase(0);
        redisProperties.setPassword(config.getProperty("spring.redis.password",""));
        //////////////////////// redis pool配置
        redisProperties.setMaxIdle(500);
        redisProperties.setMinIdle(200);
        redisProperties.setMaxActive(2000);
        redisProperties.setMaxWait(5000);
        redisProperties.setNumTestsPerEvictionRun(200);//一次最多evict的pool里的jedis实例个数
        redisProperties.setTimeBetweenEvictionRunsMillis(6000);//test idle 线程的时间间隔\
        redisProperties.setMinEvictableIdleTimeMills(60000);//连接池中连接可空闲的时间,毫秒
        redisProperties.setTestOnCreate(false);
        redisProperties.setTestOnBorrow(true);//在获取连接的时候检查有效性
        redisProperties.setTestOnReturn(false);//当调用return Object方法时，是否进行有效性检查
        redisProperties.setTestWhileIdle(true);//在空闲时检查有效性
        redisProperties.setCommandTimeout(60);
        redisProperties.setShutdownTimeout(100);

        ///////////////////////// redis 单节点配置，兼容集群问题
        redisProperties.setCluster(true); // 是否配置集群
        redisProperties.setHost("172.22.0.22");
        redisProperties.setPort(26379);
        return redisProperties;
    }
}
