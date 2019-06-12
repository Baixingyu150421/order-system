package com.bxy.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class RedisLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key  productId
     * @param value  当前时间+超时时间
     * @return
     */
    public boolean lock(String key ,String value){
        //SETNX是”SET if Not eXists”的简写
        if(redisTemplate.opsForValue().setIfAbsent(key, value)){
            return true;
        }
        //获取当前锁  为了防止在加锁后执行的业务代码由于I/O，访问数据库等抛出异常导致后面的代码无法被执行。产生死锁现象
        String currentValue = redisTemplate.opsForValue().get(key);
        //如果锁过期,锁被清空了 或 上一次锁的时间小于当前时间
        if(!StringUtils.isEmpty(currentValue)
                && Long.parseLong(currentValue) < System.currentTimeMillis()){
            //获取上一个锁的时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            //多线程并发访问时不会同时拿到锁
            if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                    return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key,String value){
        String currentValue = redisTemplate.opsForValue().get(key);
        try {
            if(!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.error("【redis分布式锁】 解锁异常，{}",e);
        }
    }
}
