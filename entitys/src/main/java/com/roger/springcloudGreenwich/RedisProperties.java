package com.roger.springcloudGreenwich;

import lombok.Data;

/**
 * Created by admin on 2019/10/25.
 */
@Data
public class RedisProperties {
    private Integer maxIdle = 8;
    private Integer minIdle = 0;
    private Integer timeout = 10000;
    private Integer db = 0;
    private String sentinelPassword = "yulei";
    private String sentinelMaster = "mymaster";
    private String sentinelNodes = "192.168.100.131:26379,192.168.100.131:26380,192.168.100.131:26380";

}
