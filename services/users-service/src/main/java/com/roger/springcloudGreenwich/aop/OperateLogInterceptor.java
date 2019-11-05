package com.roger.springcloudGreenwich.aop;

import com.google.common.collect.ImmutableList;
import com.roger.springcloudGreenwich.annotation.OperateLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by admin on 2019/11/4.
 */
@Aspect
@Configuration
@Slf4j
public class OperateLogInterceptor {

    @Around("execution(public * *(..)) && @annotation(com.roger.springcloudGreenwich.annotation.OperateLog)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        OperateLog operateLogAnnotation = method.getAnnotation(OperateLog.class);
        String name = operateLogAnnotation.name();
        String key = operateLogAnnotation.key();
        log.info("Access name={} and key = {}", name, key);
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            throw new RuntimeException("server exception");
        }
    }



}
