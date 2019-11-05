package com.roger.springcloudGreenwich.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.roger.springcloudGreenwich.User;
import com.roger.springcloudGreenwich.annotation.OperateLog;
import com.roger.springcloudGreenwich.constant.Constants;
import com.roger.springcloudGreenwich.message.KafkaSender;
import com.roger.springcloudGreenwich.result.BaseResult;
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

    //@OperateLog(key = "test", name="log", prefix = "operate")
    @PostMapping(value = "/login",produces = "text/html;charset=UTF-8")
    public String login(HttpServletRequest request,
                        @RequestBody String params) throws Exception{
        log.info("login params is:{}", params);
        User user = (User)StringUtil.jsonToObject(params, User.class);
        user.setPassword(MD5Util.md5Encode(user.getPassword()));
        List<User> list = userService.selectUsers(user);

        String resultMsg;
        if(list == null || list.isEmpty()){
            resultMsg = "false";
        }else{
            resultMsg = "success";
        }
        BaseResult baseResult = new BaseResult();
        baseResult.setResultMsg(resultMsg);
        baseResult.setIsNeedLog(false);
        return StringUtil.javabeanToJson(baseResult);
    }

    @GetMapping(value = "/getSession",produces = "text/html;charset=UTF-8")
    public Object getSession(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();

        log.info("session id is:{}", session.getId());
        if(session.getAttribute("loginUser") == null){
            log.info("session is null");
            return null;
        }else{
            log.info("session id is:{}" + session.getId());
            User user = (User)session.getAttribute("loginUser");
            return StringUtil.javabeanToJson(user);
        }
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request,
                        @RequestBody String params) throws Exception{
        log.info("login params is:{}", params);
        return "logout success";
    }

    @GetMapping(value = "/getUser")
    public Object getUser(HttpServletRequest request){
        log.info("send message to kafka");
        kafkaSender.send("hello world");
        User user = new User();
        List<User> userList = userService.selectUsers(user);
        return userList;
    }

    @GetMapping(value = "/addUser",produces = "text/html;charset=UTF-8")
    public Object addUser(HttpServletRequest request,@RequestBody String params) throws Exception{
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        User user = (User)StringUtil.jsonToObject(params, User.class);
        String userNo = user.getUserNo();
        user.setPassword(null);
        user.setUserNo(userNo.toUpperCase());
        List<User> list = userService.selectUsers(user);
        if(!list.isEmpty()){
            log.info("user {} is exist", user.getUserNo());
            return "false";
        }else{
            Long id = redisUtil.generateId("id", 1);
            user.setId(id);
            user.setUserNo(userNo);
            user.setPassword(MD5Util.md5Encode(user.getPassword()));
            userService.addUser(user);
        }

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
        BaseResult baseResult = new BaseResult();
        baseResult.setResultMsg("success");
        baseResult.setIsNeedLog(false);
        return StringUtil.javabeanToJson(baseResult);
    }
}
