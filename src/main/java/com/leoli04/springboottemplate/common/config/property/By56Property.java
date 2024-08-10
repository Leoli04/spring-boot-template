package com.leoli04.springboottemplate.common.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 对接百运快递
 * https://open.by56.com/apicus/#/common/Interface
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 18:27
 * @Version: 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "express.baiyun")
@Data
public class By56Property {
    private String host;

    private String key;

    private String secret;
}
