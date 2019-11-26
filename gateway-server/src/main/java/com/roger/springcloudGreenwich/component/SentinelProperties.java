package com.roger.springcloudGreenwich.component;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2019/11/25.
 */
@Component
public class SentinelProperties {
    Config config = ConfigService.getAppConfig();
    private String master = config.getProperty("redis.master", "");

    private String nodes = config.getProperty("redis.nodes", "");

    private Set<String> hosts;

    @PostConstruct
    public void hosts() {
        hosts = new HashSet<>();
        hosts.addAll(Arrays.asList(nodes.split(",")));
    }

    public String getMaster() {
        return master;
    }

    public String getNodes() {
        return nodes;
    }


    public Set<String> getHosts() {
        return hosts;
    }


}
