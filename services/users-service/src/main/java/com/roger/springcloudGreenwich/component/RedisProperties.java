package com.roger.springcloudGreenwich.component;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2019/11/6.
 */
@Data
@Component
public class RedisProperties {
    private String redisNodes = "192.168.100.131:26379,192.168.100.131:26380,192.168.100.131:26380";
    private String master = "mymaster";
    private int database = 0;
    private String password = "yulei";
    //////////////////////// redis pool配置
    private Integer maxIdle = 500;
    private Integer minIdle = 200;
    private Integer maxActive = 2000;
    private Integer maxWait = 5000;
    private Integer numTestsPerEvictionRun = 200;//一次最多evict的pool里的jedis实例个数
    private Integer timeBetweenEvictionRunsMillis = 6000;//test idle 线程的时间间隔\
    private Integer minEvictableIdleTimeMills = 6000;//连接池中连接可空闲的时间,毫秒
    private Boolean testOnCreate = false;
    private Boolean testOnBorrow = true;//在获取连接的时候检查有效性
    private Boolean testOnReturn = false;//当调用return Object方法时，是否进行有效性检查
    private Boolean testWhileIdle = true;//在空闲时检查有效性
    private Integer commandTimeout = 60;
    private Integer shutdownTimeout = 100;

    ///////////////////////// redis 单节点配置，兼容集群问题
    private Boolean cluster = true; // 是否配置集群
    private String host = "127.0.0.1";
    private Integer port = 23679;



}
