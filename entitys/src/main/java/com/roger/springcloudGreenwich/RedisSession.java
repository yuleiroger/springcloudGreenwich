package com.roger.springcloudGreenwich;

import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by admin on 2019/10/28.
 */
@Getter
public class RedisSession implements Serializable{
    private static final long serialVersionUID = 2751666419092554266L;
    private String sessionId = UUID.randomUUID().toString();
    private ConcurrentMap<String, Object> map = new ConcurrentHashMap();

    public Object getAttribute(String name){
        return map.get(name);
    }
    public void setAttribute(String key, Object value){
        map.put(key, value);
    }
}
