package com.roger.springcloudGreenwich.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2019/10/21.
 */
@RestController
@RequestMapping("/users-service")
public class UsersController {
    @GetMapping(value = "/login")
    public String login(){
        return "login success";
    }
}
