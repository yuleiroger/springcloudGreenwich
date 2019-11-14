package com.roger.springcloudGreenwich.component;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by admin on 2019/11/12.
 */
@Component
@Slf4j
public class HealthExamination implements IPing {
    //https://www.cnblogs.com/ye-feng-yu/p/11106006.html
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public boolean isAlive(Server server) {
        log.info("server id is===={}",server.getId());

        String url = "http://" + server.getId() + "/health";

        try {
            ResponseEntity<String> heath = restTemplate.getForEntity(url, String.class);
            if (heath.getStatusCode() == HttpStatus.OK) {
                log.info("ping {} success and response is {}", url, heath.getBody());
                return true;
            }
            log.info("ping {} error and response is {}", url, heath.getBody());
            return false;
        }
        catch (Exception e){
            log.info("ping {} failed", url);
            return false;
        }

    }
}
