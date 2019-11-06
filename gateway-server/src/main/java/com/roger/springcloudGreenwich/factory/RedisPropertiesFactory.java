package com.roger.springcloudGreenwich.factory;

import com.roger.springcloudGreenwich.RedisProperties;

/**
 * Created by admin on 2019/11/6.
 */
public abstract class RedisPropertiesFactory implements IRedisPropertiesFactory{
    @Override
    public RedisProperties generateRedisProperties() {
        return null;
    }
}
