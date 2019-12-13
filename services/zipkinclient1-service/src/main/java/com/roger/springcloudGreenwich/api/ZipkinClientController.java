package com.roger.springcloudGreenwich.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by admin on 2019/12/12.
 */
@RestController
public class ZipkinClientController {
    private String url = "http://localhost:8081/index";

    @GetMapping(value = "index")
    public String index(){
        return "index";
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getHello")
    public String getHello(){
        return this.restTemplate.getForObject(this.url, String.class);
    }
}
