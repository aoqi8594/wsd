package com.qzsoft.tah.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author aq
 * @version 1.0 2021/7/27
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean expire(String key,long seconds){
        return redisTemplate.expire(key,seconds, TimeUnit.SECONDS);
    }

    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    public void set (String key, String value,long seconds){
        if(seconds>0){
            redisTemplate.opsForValue().set(key,value,seconds,TimeUnit.SECONDS);
        }else {
            redisTemplate.opsForValue().set(key,value);
        }
    }

}
