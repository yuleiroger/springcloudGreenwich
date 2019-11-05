package com.roger.springcloudGreenwich.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2019/11/5.
 */
@RestController
@RequestMapping("/log-service")
@Slf4j
public class LogController {

    @GetMapping("/index")
    public Object index(){
        return "log service success";
    }

}
