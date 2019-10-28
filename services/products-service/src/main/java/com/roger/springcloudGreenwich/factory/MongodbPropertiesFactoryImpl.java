package com.roger.springcloudGreenwich.factory;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.roger.springcloudGreenwich.entity.MongoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2019/10/28.
 */

@Slf4j
public class MongodbPropertiesFactoryImpl extends MongodbPropertiesFactory implements MongodbPropertiesFactoryInterface{

    @Override
    public MongoProperties generateMongoProperties(String type) {
        Config config = ConfigService.getAppConfig();
        MongoProperties mongoProperties = new MongoProperties();
        mongoProperties.setDatabase(config.getProperty("mongo.cluster.database", ""));
        String address = config.getProperty("mongo.cluster.address", "");
        String[] addressArray = address.split(",");
        List<String> addressList = Arrays.asList(addressArray);
        mongoProperties.setAddress(addressList);
        mongoProperties.setMinConnectionsPerHost(config.getIntProperty("mongo.cluster.minConnectionsPerHost",0));
        mongoProperties.setMaxConnectionsPerHost(config.getIntProperty("mongo.cluster.maxConnectionsPerHost", 0));
        mongoProperties.setThreadsAllowedToBlockForConnectionMultiplier(config.getIntProperty("mongo.cluster.threadsAllowedToBlockForConnectionMultiplier", 0));
        return mongoProperties;
    }
}
