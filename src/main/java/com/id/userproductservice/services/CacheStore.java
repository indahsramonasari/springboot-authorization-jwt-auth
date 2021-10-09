package com.id.userproductservice.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CacheStore {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void storeCache(String keyCache, Object cacheValue, long lifetime){
        redisTemplate.opsForValue().set(keyCache, cacheValue, lifetime, TimeUnit.SECONDS);
    }

    public Object getCache(String keyCache){
        return redisTemplate.opsForValue().get(keyCache);
    }

    public void removeCache (String keyCache){
        redisTemplate.opsForValue().getOperations().delete(keyCache);
    }
}
