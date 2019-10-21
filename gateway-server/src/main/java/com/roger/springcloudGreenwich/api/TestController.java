package com.roger.springcloudGreenwich.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2019/10/21.
 */
@RestController
public class TestController {
    @GetMapping(value = "/test")
    public String test(){
        return "test success";
    }
}
