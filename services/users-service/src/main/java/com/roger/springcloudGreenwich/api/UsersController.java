package com.roger.springcloudGreenwich.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.roger.springcloudGreenwich.RedisSession;
import com.roger.springcloudGreenwich.User;
import com.roger.springcloudGreenwich.annotation.Limit;
import com.roger.springcloudGreenwich.constant.Constants;
import com.roger.springcloudGreenwich.jwt.JwtUtil;
import com.roger.springcloudGreenwich.message.KafkaSender;
import com.roger.springcloudGreenwich.result.BaseResult;
import com.roger.springcloudGreenwich.service.UserService;
import com.roger.springcloudGreenwich.util.RedisUtil;
import com.roger.springcloudGreenwich.utils.MD5Util;
import com.roger.springcloudGreenwich.utils.StringUtil;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Roger on 2019/10/21.
 * 用户controller层
 */
@RestController
@Slf4j
@RequestMapping("/users-service")
public class UsersController {
    @Autowired
    private RedisUtil userServiceRedisUtil;
    @Autowired
    private KafkaSender kafkaSender;
    @Autowired
    private UserService userService;

    //@Limit(key = "test", period = 100, count = 2, name="resource", prefix = "limit")
    @PostMapping(value = "/login",produces = "text/html;charset=UTF-8")
    public String login(@RequestParam String params) throws Exception{
        log.info("login params is:{}", params);
        User user = (User)StringUtil.jsonToObject(params, User.class);
        user.setPassword(MD5Util.md5Encode(user.getPassword()));

        boolean queryFromRedis = false;
        String resultMsg = null;

        //query from redis firstly
        Object redidResult = userServiceRedisUtil.HGet("users", user.getUserNo());
        log.info("query from redis result is:{}", redidResult);
        if(redidResult != null){
            User redisUser = (User)redidResult;
            if(redisUser.getPassword().equals(user.getPassword())){
                log.info("query from redis user is={}",redisUser);
                resultMsg = Constants.SUCCESS;
                queryFromRedis = true;
                userServiceRedisUtil.expandExpiration(user.getUserNo(), 4 * 30);
            }
        }
        //query from mysql
        if(!queryFromRedis){
            List<User> list = userService.selectUsers(user);
            if(list == null || list.isEmpty()){
                resultMsg = Constants.FALSE;
            }else{
                //login success first time
                userServiceRedisUtil.HSet("users", user.getUserNo(), user, 120L);
                resultMsg = Constants.SUCCESS;
            }
        }
        BaseResult baseResult = new BaseResult();
        if(Constants.SUCCESS.equals(resultMsg)){
            String jwt = JwtUtil.getToken(user.getUserNo());
            String token = "Bearer:" +jwt;
            baseResult.setToken(token);
            userServiceRedisUtil.setObject(token, user, null);

        }

        baseResult.setResultMsg(resultMsg);
        baseResult.setIsNeedLog(false);
        return StringUtil.javabeanToJson(baseResult);
    }

    @GetMapping(value = "/getSession",produces = "text/html;charset=UTF-8")
    public Object getSession(HttpServletRequest request) throws Exception{
        ConcurrentMap<String,RedisSession> map = (ConcurrentHashMap)userServiceRedisUtil.getRedisValue("session");
        RedisSession session = new RedisSession();

        log.info("session id is:{}", session.getSessionId());
        if(session.getAttribute("loginUser") == null){
            log.info("session is null");
            return null;
        }else{
            User user = (User)session.getAttribute("loginUser");
            return StringUtil.javabeanToJson(user);
        }
    }

    @GetMapping(value = "/logout")
    public String logout(@RequestBody String params) throws Exception{
        log.info("login params is:{}", params);
        return "logout success";
    }

    @GetMapping(value = "/getUser")
    public Object getUser(){
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
            Long id = userServiceRedisUtil.generateId("id", 1);
            user.setId(id);
            user.setUserNo(userNo);
            user.setPassword(MD5Util.md5Encode(user.getPassword()));
            userService.addUser(user);
        }

        return "success";
    }

    @GetMapping(value = "/addUserBatch",produces = "text/html;charset=UTF-8")
    public Object addUserBatch() throws Exception{
        long time = userService.batchTest();
        BaseResult baseResult = new BaseResult();
        baseResult.setResultMsg("success");
        baseResult.setIsNeedLog(false);
        baseResult.setTime(time);
        return StringUtil.javabeanToJson(baseResult);
    }

    @GetMapping(value = "/addUserNoBatch",produces = "text/html;charset=UTF-8")
    public Object addUserNoBatch() throws Exception{
        long time = userService.noBatchTest();
        BaseResult baseResult = new BaseResult();
        baseResult.setResultMsg("success");
        baseResult.setIsNeedLog(false);
        baseResult.setTime(time);
        return StringUtil.javabeanToJson(baseResult);
    }


    @HystrixCommand(fallbackMethod = "error", commandProperties = {
            @HystrixProperty(name="execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),//请求时间，毫秒
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

    /**
     * 降级方法里的参数需要和主方法一致
     * @param request
     * @return
     */
    public Object error(HttpServletRequest request){
        BaseResult baseResult = new BaseResult();
        baseResult.setResultMsg("success");
        baseResult.setIsNeedLog(false);
        return StringUtil.javabeanToJson(baseResult);
    }

    @GetMapping("/exp")
    public void generateException(){
        int i = 1/0;
    }


}
