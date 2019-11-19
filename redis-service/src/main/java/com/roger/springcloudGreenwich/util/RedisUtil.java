package com.roger.springcloudGreenwich.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2019/1/30.
 */
@Component
public class RedisUtil {
    private static RedisTemplate<String,Object> redisTemplate;

    public RedisUtil(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

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

    public void setList(String key , Object value, Long expireTime){
        redisTemplate.opsForList().leftPush(key, value);
        if(expireTime == null){
            expireTime = 30L;
        }
        redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
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

    public void HSet(String redisKey, String mapKey, Object value, Long expireTime){
        redisTemplate.opsForHash().put(redisKey, mapKey, value);
        if(expireTime == null){
            expireTime = 30L;
        }
        redisTemplate.expire(redisKey, expireTime, TimeUnit.MINUTES);
    }

    public Object HGet(String redisKey, String field){
        return redisTemplate.opsForHash().get(redisKey, field);
    }


    public Set<?> getSet(String key){
        Set<?> set = redisTemplate.opsForSet().members(key);
        return set;
    }

    public Long generateId(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        entityIdCounter.expire(-1, TimeUnit.SECONDS);
        return increment;
    }

    public long generateId(String key, int increment) {
        Format f = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天

        Date tomorrow = c.getTime();

        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());

        counter.expireAt(tomorrow);
        redisTemplate.expire(key, -1 , TimeUnit.MINUTES);
        return counter.addAndGet(increment);
    }


    public Object incrementHash(RedisScript script, String hashKey, Long delta, final List<String> fields) throws Exception{
        List<String> keyList = new ArrayList();
        keyList.add("count");
        keyList.add("rate.limiting:127.0.0.1");

/**
 * 用Mpa设置Lua的ARGV[1]
 */
        Map<String, Object> argvMap = new HashMap<String, Object>();
        argvMap.put("expire", 10000);
        argvMap.put("times", 10);
        Object obj = redisTemplate.execute(script, keyList, argvMap);
        System.out.println(obj);
        return redisTemplate.execute(script, keyList, argvMap);
    }

    public void getCount(String redisScript, List<String> keys){
        //redisTemplate.ex.execute(redisScript, keys)
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
