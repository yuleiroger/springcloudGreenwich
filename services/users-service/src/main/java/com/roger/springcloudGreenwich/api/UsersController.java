package com.roger.springcloudGreenwich.api;

import com.roger.springcloudGreenwich.User;
import com.roger.springcloudGreenwich.constant.Constants;
import com.roger.springcloudGreenwich.util.RedisUtil;
import com.roger.springcloudGreenwich.utils.MD5Util;
import com.roger.springcloudGreenwich.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private RedisUtil redisUtil;

    @GetMapping(value = "/login")
    public String login(HttpServletResponse response) throws Exception{
        log.info("login");
        User user = new User();
        String key = MD5Util.md5Encode("admin");
        user.setUserNo("admin");
        user.setUserName(StringUtil.getRandomString(4));
        user.setPassword("123456");
        redisUtil.setObject(key, user, null);
        String cookieName="Sender";
        Cookie cookie = new Cookie(cookieName, "Test_Content");
        response.addCookie(cookie);
        return "login success";
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
