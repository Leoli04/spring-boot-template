package com.leoli04.springboottemplate.common.config;

import lombok.Getter;

/**
 * @Description: 缓存名字
 * @Author: LeoLi04
 * @CreateDate: 2024/7/27 21:39
 * @Version: 1.0
 */
@Getter
public enum CacheNames {
    //
    demo,
    demo_ttl(7200),
    DEMO_CACHE(7200,2000),
    ;

    private static final int DEFAULT_MAXSIZE = 1000;
    public static final int DEFAULT_TTL = 3600;
    CacheNames() {
    }

    CacheNames(int ttl) {
        this.ttl = ttl;
    }

    CacheNames(int ttl, int maxSize) {
        this.ttl = ttl;
        this.maxSize = maxSize;
    }

    /**
     * 缓存过期时间（秒）
     *
     */
    private int ttl = DEFAULT_TTL;
    /**
     * 最大数量
     */
    private int maxSize = DEFAULT_MAXSIZE;


}
