package com.roger.springcloudGreenwich.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2019/10/25.
 */
@Component
@Data
public class MongoProperties {
//    private String database = "admin";
//    String[] addressArray = {"192.168.100.129:27017","192.168.100.129:27018","192.168.100.129:27019"};
//    private List<String> address = Arrays.asList(addressArray);
//    private Integer minConnectionsPerHost = 0;
//    private Integer maxConnectionsPerHost = 100;
//    private Integer threadsAllowedToBlockForConnectionMultiplier = 5;
//    private Integer serverSelectionTimeout = 30000;
//    private Integer maxWaitTime = 120000;
//    private Integer maxConnectionIdleTime = 0;
//    private Integer maxConnectionLifeTime = 0;
//    private Integer connectTimeout = 10000;
//    private Integer socketTimeout = 0;
//    private Boolean socketKeepAlive = false;
//    private Boolean sslEnabled = false;
//    private Boolean sslInvalidHostNameAllowed = false;
//    private Boolean alwaysUseMBeans = false;
//    private Integer heartbeatFrequency = 10000;
//    private Integer minHeartbeatFrequency = 500;
//    private Integer heartbeatConnectTimeout = 20000;
//    private Integer heartbeatSocketTimeout = 20000;
//    private Integer localThreshold = 15;

    private String database;
    private List<String> address;
    private Integer minConnectionsPerHost;
    private Integer maxConnectionsPerHost;
    private Integer threadsAllowedToBlockForConnectionMultiplier;
    private Integer serverSelectionTimeout;
    private Integer maxWaitTime;
    private Integer maxConnectionIdleTime;
    private Integer maxConnectionLifeTime;
    private Integer connectTimeout;
    private Integer socketTimeout;
    private Boolean socketKeepAlive;
    private Boolean sslEnabled;
    private Boolean sslInvalidHostNameAllowed;
    private Boolean alwaysUseMBeans;
    private Integer heartbeatFrequency;
    private Integer minHeartbeatFrequency;
    private Integer heartbeatConnectTimeout;
    private Integer heartbeatSocketTimeout;
    private Integer localThreshold;

}
