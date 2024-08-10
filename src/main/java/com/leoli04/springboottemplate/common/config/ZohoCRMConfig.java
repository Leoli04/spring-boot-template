package com.leoli04.springboottemplate.common.config;

import com.zoho.api.authenticator.OAuthToken;
import com.zoho.api.authenticator.Token;
import com.zoho.api.authenticator.store.FileStore;
import com.zoho.api.authenticator.store.TokenStore;
import com.zoho.api.logger.Logger;
import com.zoho.crm.api.Initializer;
import com.zoho.crm.api.SDKConfig;
import com.zoho.crm.api.dc.CNDataCenter;
import com.zoho.crm.api.dc.DataCenter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengtao.zhang
 * @create 2024-06-06 23:48
 * @desc zoho crm配置类
 **/
@Configuration
@ConfigurationProperties(prefix = "zoho.crm")
@Data
@Slf4j
public class ZohoCRMConfig {

    private String clientId;

    private String clientSecret;

    private String grantToken;

    private String logPath;

    private String filePath;

    private String resourcePath;
    /**
     * CNDataCenter.PRODUCTION
     * CNDataCenter.SANDBOX
     */
    private String env;

    @Bean
    public Initializer initialize() throws Exception {
        Logger logger = new Logger.Builder().level(Logger.Levels.INFO).filePath(logPath).build();
        DataCenter.Environment environment = env.equalsIgnoreCase("SANDBOX") ? CNDataCenter.SANDBOX : CNDataCenter.PRODUCTION;
        Token token = new OAuthToken.Builder().clientID(clientId).clientSecret(clientSecret).grantToken(grantToken).build();
        TokenStore tokenstore = new FileStore(filePath);
        SDKConfig sdkConfig = new SDKConfig.Builder().autoRefreshFields(false).pickListValidation(true).build();
        new Initializer.Builder()
                .environment(environment)
                .token(token).store(tokenstore)
                .SDKConfig(sdkConfig)
                .resourcePath(resourcePath)
                .logger(logger)
                .initialize();
        return Initializer.getInitializer();
    }
}
