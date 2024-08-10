package com.leoli04.springboottemplate.common.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 腾讯云 cos 配置
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 21:35
 * @Version: 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "tencent.api")
@Data
@Slf4j
public class TencentCosConfig {
    private String appId;
    private String secretId;
    private String secretKey;
    private String bucketName;
    private String region;
    private String icDomain;

    @Bean
    public COSClient initCOSClient(){
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);
        log.info("initCOSClient 成功.");
        return cosClient;
    }
}
