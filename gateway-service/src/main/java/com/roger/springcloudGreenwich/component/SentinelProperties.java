package com.roger.springcloudGreenwich.component;

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
    private String master = "mymaster";

    private String nodes = "192.168.100.131:26379,192.168.100.131:26380,192.168.100.131:26380";

    private Set<String> hosts;

    @PostConstruct
    public void hosts() {
        hosts = new HashSet<>();
        hosts.addAll(Arrays.asList(nodes.split(",")));
    }

    public String getMaster() {
        return master;
    }




    public Set<String> getHosts() {
        return hosts;
    }


}
