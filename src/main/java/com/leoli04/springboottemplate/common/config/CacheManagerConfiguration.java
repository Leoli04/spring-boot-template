package com.leoli04.springboottemplate.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 缓存管理配置，使用@Cacheable
 * @Author: LeoLi04
 * @CreateDate: 2024/7/27 21:34
 * @Version: 1.0
 */
@EnableCaching
@Configuration
public class CacheManagerConfiguration {

    /**
     * 创建基于Caffeine的Cache Manager,本地缓存
     * 使用 @Cacheable 注解的时候默认使用的这个缓存
     */
    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caches = Lists.newArrayList();

        Arrays.stream(CacheNames.values()).forEach(c ->
                caches.add(new CaffeineCache(c.name(),
                        Caffeine.newBuilder()
                                .recordStats()
                                .expireAfterWrite(c.getTtl(), TimeUnit.SECONDS)
                                .maximumSize(c.getMaxSize())
                                .build()))
        );
        cacheManager.setCaches(caches);

        return cacheManager;
    }

    /**
     * redis缓存
     * 使用 @Cacheable 需要指定 cacheManager = "redisCacheManager"
     * @param applicationName
     * @param factory
     * @return
     */
    @Bean(name = "redisCacheManager")
    public CacheManager redisCacheManager(@Value("${spring.application.name}") String applicationName,RedisConnectionFactory factory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .prefixCacheNameWith(applicationName)
                .entryTtl(Duration.ofSeconds(3600));

        Map<String, RedisCacheConfiguration> cacheMap = new LinkedHashMap<>(CacheNames.values().length);
        for (CacheNames c : CacheNames.values()) {
            if (c.getTtl() > 0) {
                cacheMap.put(c.name(), defaultCacheConfig.entryTtl(Duration.ofSeconds(c.getTtl())));
            } else {
                cacheMap.put(c.name(), defaultCacheConfig);
            }
        }
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig, cacheMap);
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.initializeCaches();
        return redisCacheManager;
    }
}
