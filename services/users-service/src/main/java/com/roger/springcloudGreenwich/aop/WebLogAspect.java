package com.roger.springcloudGreenwich.aop;

import com.roger.springcloudGreenwich.message.KafkaSender;
import com.roger.springcloudGreenwich.result.BaseResult;
import com.roger.springcloudGreenwich.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by yulei on 2019/11/4.
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {
    @Autowired
    private KafkaSender kafkaSender;

    @Pointcut("execution(public * com.roger.springcloudGreenwich.api..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("URL : {}" , request.getRequestURL().toString());
        log.info("HTTP_METHOD : {}" , request.getMethod());
        log.info("IP : {}" , request.getRemoteAddr());
        log.info("CLASS_METHOD : {}.{}" , joinPoint.getSignature().getDeclaringTypeName()  , joinPoint.getSignature().getName());
        log.info("ARGS : {}" , Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.info("RESPONSE : {}" ,ret);

        if(!StringUtils.isEmpty(ret)){
            try {
                BaseResult baseResult = (BaseResult) StringUtil.jsonToObject(ret.toString(), BaseResult.class);
                if(baseResult.getIsNeedLog()){
                    log.info("save log");
                    kafkaSender.send(baseResult.getResultMsg());
                }else{
                    log.info("do not save log");
                }
            }catch(Exception e){
                log.info("can not cast to BaseResult");
                e.printStackTrace();
            }

        }
    }

}
