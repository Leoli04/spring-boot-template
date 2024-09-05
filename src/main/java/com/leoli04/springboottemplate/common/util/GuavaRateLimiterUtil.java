package com.leoli04.springboottemplate.common.util;

import com.google.common.util.concurrent.RateLimiter;
import com.leoli04.springboottemplate.common.exception.LimitException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 限流工具
 * @Author: LeoLi04
 * @CreateDate: 2024/8/25 16:21
 * @Version: 1.0
 */
public class GuavaRateLimiterUtil {
    private final static GuavaRateLimiterUtil INSTANCE = new GuavaRateLimiterUtil();

    private ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();

    private GuavaRateLimiterUtil() {
    }

    /**
     * 单例
     *
     * @return 返回单例对象
     */
    public static GuavaRateLimiterUtil getInstance() {

        return INSTANCE;
    }

    /**
     * 是否有对象的限流
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return limiters.containsKey(key);
    }

    /**
     * 设置限流值
     *
     * @param key              请求key
     * @param permitsPerSecond 一秒内的限流数
     */
    public void setPermits(String key, Double permitsPerSecond) {
        if(hasKey(key)){
            throw new LimitException("500","已初始化限流策略");
        }
        limiters.put(key, RateLimiter.create(permitsPerSecond));
    }

    public boolean tryAcquire(String key) {
        return tryAcquire(key,1,1, TimeUnit.SECONDS);
    }

    public boolean tryAcquire(String key,int permits, long timeout, TimeUnit unit) {
        RateLimiter limiter = limiters.get(key);
        if (limiter == null) {
            throw new LimitException("500","未初始化限流策略");
        }
        return limiter.tryAcquire(permits,timeout,unit);
    }

    public void acquire(String key) {
        RateLimiter limiter = limiters.get(key);
        if (limiter == null) {
            throw new LimitException("500","未初始化限流策略");
        }
        limiter.acquire();
    }

}
