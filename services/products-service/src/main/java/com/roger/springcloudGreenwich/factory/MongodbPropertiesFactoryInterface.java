package com.roger.springcloudGreenwich.factory;

import com.roger.springcloudGreenwich.entity.MongoProperties;

/**
 * Created by admin on 2019/10/28.
 */
public interface MongodbPropertiesFactoryInterface {
    MongoProperties generateMongoProperties(String type);
}
