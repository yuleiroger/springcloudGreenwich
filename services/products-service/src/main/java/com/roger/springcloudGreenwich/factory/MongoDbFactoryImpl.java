package com.roger.springcloudGreenwich.factory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.roger.springcloudGreenwich.entity.MongoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2019/10/28.
 */
@Component
@Slf4j
public class MongoDbFactoryImpl {

    //覆盖默认的MongoDbFacotry
    @Bean
    @ConditionalOnMissingBean(name = "mongoDbFactory")
    public MongoDbFactory mongoDbFactory() {
        MongodbPropertiesFactory factory = new MongodbPropertiesFactoryImpl();
        MongoProperties properties = factory.generateMongoProperties("");
        properties.setServerSelectionTimeout(30000);
        properties.setMaxWaitTime(120000);
        properties.setMaxConnectionIdleTime(0);
        properties.setMaxConnectionLifeTime(0);
        properties.setConnectTimeout(10000);
        properties.setSocketTimeout(0);
        properties.setSocketKeepAlive(false);
        properties.setSslEnabled(false);
        properties.setSslInvalidHostNameAllowed(false);
        properties.setAlwaysUseMBeans(false);
        properties.setHeartbeatFrequency(10000);
        properties.setMinHeartbeatFrequency(500);
        properties.setHeartbeatConnectTimeout(20000);
        properties.setHeartbeatSocketTimeout(20000);
        properties.setLocalThreshold(15);
        //
        //客户端配置（连接数，副本集群验证）
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(properties.getMaxConnectionsPerHost());
        builder.minConnectionsPerHost(properties.getMinConnectionsPerHost());
// if (properties.getReplicaSet() != null) {
// builder.requiredReplicaSetName(properties.getReplicaSet());
// }

        builder.threadsAllowedToBlockForConnectionMultiplier(
                properties.getThreadsAllowedToBlockForConnectionMultiplier());
        builder.serverSelectionTimeout(properties.getServerSelectionTimeout());
        builder.maxWaitTime(properties.getMaxWaitTime());
        builder.maxConnectionIdleTime(properties.getMaxConnectionIdleTime());
        builder.maxConnectionLifeTime(properties.getMaxConnectionLifeTime());
        builder.connectTimeout(properties.getConnectTimeout());
        builder.socketTimeout(properties.getSocketTimeout());
//        builder.socketKeepAlive(properties.getSocketKeepAlive());
        builder.sslEnabled(properties.getSslEnabled());
        builder.sslInvalidHostNameAllowed(properties.getSslInvalidHostNameAllowed());
        builder.alwaysUseMBeans(properties.getAlwaysUseMBeans());
        builder.heartbeatFrequency(properties.getHeartbeatFrequency());
        builder.minHeartbeatFrequency(properties.getMinHeartbeatFrequency());
        builder.heartbeatConnectTimeout(properties.getHeartbeatConnectTimeout());
        builder.heartbeatSocketTimeout(properties.getHeartbeatSocketTimeout());
        builder.localThreshold(properties.getLocalThreshold());
        MongoClientOptions mongoClientOptions = builder.build();
        // MongoDB地址列表
        List<ServerAddress> serverAddresses = new ArrayList<>();
        for (String address : properties.getAddress()) {
            String[] hostAndPort = address.split(":");
            String host = hostAndPort[0];
            Integer port = Integer.parseInt(hostAndPort[1]);
            ServerAddress serverAddress = new ServerAddress(host, port);
            serverAddresses.add(serverAddress);
        }
        //System.out.println("serverAddresses:" + serverAddresses.toString());​
        // 连接认证
// MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(properties.getUsername(),
//                properties.getAuthenticationDatabase() != null ? properties.getAuthenticationDatabase() : properties.getDatabase(),
//                properties.getPassword().toCharArray());
//​​
        //创建客户端和Factory
        MongoClient mongoClient = new MongoClient(serverAddresses, mongoClientOptions);
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, properties.getDatabase());
        log.info("mongodb注入成功");
        return mongoDbFactory;
    }
}
