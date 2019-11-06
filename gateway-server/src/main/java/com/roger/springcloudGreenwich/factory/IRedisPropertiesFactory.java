package com.roger.springcloudGreenwich.factory;

import com.roger.springcloudGreenwich.RedisProperties;

/**
 * Created by admin on 2019/11/6.
 */
public interface IRedisPropertiesFactory {
    RedisProperties generateRedisProperties();
}
