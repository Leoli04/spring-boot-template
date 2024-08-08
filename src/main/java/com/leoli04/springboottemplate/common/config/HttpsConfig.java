package com.leoli04.springboottemplate.common.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 配置同时支持 HTTP 与 HTTPS 访问
 * @Author: LeoLi04
 * @CreateDate: 2024/8/8 16:48
 * @Version: 1.0
 */
@Configuration
public class HttpsConfig {

    @Value("${server.httpPort}")
    private Integer httpPort;

    @Bean
    public ServletWebServerFactory serverFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
        return tomcat;
    }
    /**
     * 配置http
     * @return
     */
    private Connector createStandardConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(httpPort);
        connector.setScheme("http");
        return connector;
    }

}
