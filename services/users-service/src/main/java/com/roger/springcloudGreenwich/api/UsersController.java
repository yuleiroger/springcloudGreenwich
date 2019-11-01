package com.roger.springcloudGreenwich.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.roger.springcloudGreenwich.User;
import com.roger.springcloudGreenwich.constant.Constants;
import com.roger.springcloudGreenwich.message.KafkaSender;
import com.roger.springcloudGreenwich.service.UserService;
import com.roger.springcloudGreenwich.util.RedisUtil;
import com.roger.springcloudGreenwich.utils.MD5Util;
import com.roger.springcloudGreenwich.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2019/10/21.
 */
@RestController
@Slf4j
@RequestMapping("/users-service")
public class UsersController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private KafkaSender kafkaSender;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public String login(HttpServletResponse response,@RequestBody String params) throws Exception{
        log.info("login params is:{}", params);
        User user = new User();
        String key = MD5Util.md5Encode("admin");

        user.setUserNo("admin");
        user.setUserName(StringUtil.getRandomString(4));
        user.setPassword("123456");
        redisUtil.setObject(key, user, null);
        String cookieName="Sender";
        Cookie cookie = new Cookie(cookieName, "Test_Content");
        response.addCookie(cookie);
        Map<String, String> map = new HashMap<>();
        map.put("result","success");
        log.info("return result is{}",StringUtil.javabeanToJson(map));
        return StringUtil.javabeanToJson(map);
    }

    @GetMapping(value = "/getUser")
    public Object getUser(HttpServletRequest request){
        log.info("send message to kafka");
        kafkaSender.send("hello world");
        User user = new User();
        List<User> userList = userService.selectUsers(user);
        return userList;
    }

    @GetMapping(value = "/addUser")
    public Object addUser(HttpServletRequest request) throws Exception{
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        User user = new User();
        Long id = redisUtil.generateId("id", 1);
        log.info("id is:{}", id);
        user.setId(id);
        user.setUserNo(StringUtil.getRandomString(5));
        user.setPassword(MD5Util.md5Encode("123456"));

        userService.addUser(user);

        return "success";
    }


    @HystrixCommand(fallbackMethod = "error", commandProperties = {
            @HystrixProperty(name="execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "40")
    }, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "1"),
            @HystrixProperty(name = "maxQueueSize", value = "10"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "1000"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "8"),
            @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
    })
    @GetMapping(value = "/helloHystrix")
    public Object helloHystrix(HttpServletRequest request) throws Exception{
        //int i = 1/0;
        Thread.sleep(3000);
        kafkaSender.send("hello world");
        return "send success";
    }

    public Object error(HttpServletRequest request){
        return "error";
    }
}
