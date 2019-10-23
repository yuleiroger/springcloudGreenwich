package com.roger.springcloudGreenwich.api;

import com.roger.springcloudGreenwich.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2019/10/21.
 */
@RestController
@Slf4j
@RequestMapping("/users-service")
public class UsersController {
    @Autowired
    private HttpSession session;

    @GetMapping(value = "/login")
    public String login(){
        log.info("login");
        session.setAttribute(Constants.TestKey, Constants.TestValue);
        return session.getId();
    }

    @GetMapping(value = "/getUser")
    public Object getUser(HttpSession session){
        log.info("get");
        session.setAttribute("testKey", "testValue");
        Map<String, Object> map = new HashMap<>();
        map.put("test", session.getAttribute("testKey"));
        return map;
    }
}
