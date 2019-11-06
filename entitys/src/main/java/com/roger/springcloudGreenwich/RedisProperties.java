package com.roger.springcloudGreenwich;

import lombok.Data;

/**
 * Created by admin on 2019/11/6.
 */
@Data
public class RedisProperties {
    private String redisNodes;
    private String master;
    private int database;
    private String password;
    //////////////////////// redis pool配置
    private Integer maxIdle;
    private Integer minIdle;
    private Integer maxActive;
    private Integer maxWait;
    private Integer numTestsPerEvictionRun;//一次最多evict的pool里的jedis实例个数
    private Integer timeBetweenEvictionRunsMillis;//test idle 线程的时间间隔\
    private Integer minEvictableIdleTimeMills;//连接池中连接可空闲的时间,毫秒
    private Boolean testOnCreate;
    private Boolean testOnBorrow;//在获取连接的时候检查有效性
    private Boolean testOnReturn;//当调用return Object方法时，是否进行有效性检查
    private Boolean testWhileIdle;//在空闲时检查有效性
    private Integer commandTimeout;
    private Integer shutdownTimeout;

    ///////////////////////// redis 单节点配置，兼容集群问题
    private Boolean cluster; // 是否配置集群
    private String host;
    private Integer port;
}
