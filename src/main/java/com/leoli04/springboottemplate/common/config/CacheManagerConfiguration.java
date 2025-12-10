package com.leoli04.springboottemplate.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 缓存管理配置，使用@Cacheable
 * @Author: LeoLi04
 * @CreateDate: 2024/7/27 21:34
 * @Version: 1.0
 */
@EnableCaching
@Configuration
@Slf4j
public class CacheManagerConfiguration {

    /**
     * 创建基于Caffeine的Cache Manager,本地缓存
     * 使用 @Cacheable 注解的时候默认使用的这个缓存
     */
    @Bean(name = "caffeineCacheManager")
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
                                .softValues()
                                .removalListener((key, value, cause) ->
                                        log.debug("缓存 {} 被移除，原因: {}", key, cause))
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
    public CacheManager redisCacheManager(@Value("${spring.application.name}") String applicationName,
                                          RedisConnectionFactory factory) {
        // 默认配置
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .prefixCacheNameWith(applicationName + ":")
                .entryTtl(Duration.ofSeconds(CacheNames.DEFAULT_TTL))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // 为每个 CacheNames 配置独立 TTL
        Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
        for (CacheNames cn : CacheNames.values()) {
            cacheConfigs.put(cn.name(), defaultConfig.entryTtl(Duration.ofSeconds(cn.getTtl())));
        }

        return RedisCacheManager.builder(factory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigs)
                .transactionAware()
                .build();
    }
}
