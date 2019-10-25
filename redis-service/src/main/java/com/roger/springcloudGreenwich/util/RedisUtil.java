package com.roger.springcloudGreenwich.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2019/1/30.
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    public Object getRedisValue(String key){
        Object redisValue = redisTemplate.opsForValue().get(key);
        return redisValue;
    }

    public void expandExpiration(String key,Integer expiration){
        redisTemplate.expire(key,expiration, TimeUnit.MINUTES);
    }

    public void deleteRedisKey(String key){
        redisTemplate.delete(key);
    }

    public Set<String> getKeys(){
        return redisTemplate.keys("*");
    }

    public void setList(String key , Object value, Long expireTime){
        redisTemplate.opsForList().leftPush(key, value);
        if(expireTime == null){
            expireTime = 30L;
        }

    }

    public void setObject(String key, Object obj, Long expireTime){
        if(expireTime == null){
            expireTime = 30L;
        }
        redisTemplate.opsForValue().set(key, obj);
        redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
    }


    public void removeFromList(String key, Object value){
        redisTemplate.opsForList().remove(key, 1, value);
    }

    public List<?> getList(String key, Long begin, Long end){
        if(begin == null){
           begin = 0L;
        }
        if(end == null){
            end = -1L;
        }
        List<?> list = redisTemplate.opsForList().range(key, begin, end);
        return list;
    }

    public void setSet(String key, Object value, long expireTime){
        redisTemplate.opsForSet().add(key, value);
        redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
    }

    public Set<?> getSet(String key){
        Set<?> set = redisTemplate.opsForSet().members(key);
        return set;
    }

    public static void main(String[] args) {
        Integer i1 = 128;
        Integer i2 = 128;
        System.out.println(i1 == i2);

        Integer i3 = 100;
        Integer i4 = 100;
        System.out.println(i3 == i4);

        int age = 6;
        if(age >= 5 && age <= 7){

        }

    }

}
