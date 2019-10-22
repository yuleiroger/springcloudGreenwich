package com.roger.springcloudGreenwich.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2019/10/21.
 */
@RestController
@Slf4j
@RequestMapping("/users-service")
public class UsersController {
    @GetMapping(value = "/login")
    public String login(){
        log.info("login");
        return "login success";
    }

    @GetMapping(value = "/getUser")
    public String getUser(){
        log.info("get");
        return "get success";
    }
}
