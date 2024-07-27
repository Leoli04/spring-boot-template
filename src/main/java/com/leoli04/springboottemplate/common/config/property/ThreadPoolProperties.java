package com.leoli04.springboottemplate.common.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 线程池配置
 * @Author: LeoLi04
 * @CreateDate: 2024/7/27 17:03
 * @Version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPoolProperties {

    private Integer corePoolSize=8;
    private Integer maxPoolSize=16;
    private Integer queueCapacity=1000;
}
