package com.roger.springcloudGreenwich.factory.redis;

import com.roger.springcloudGreenwich.RedisProperties;

/**
 * Created by yulei on 2019/11/6.
 */
public abstract class RedisPropertiesFactory implements IRedisPropertiesFactory{
    @Override
    public RedisProperties generateRedisProperties() {
        return null;
    }
}
