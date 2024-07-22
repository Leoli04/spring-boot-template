package com.leoli04.springboottemplate.common.filter;

import com.leoli04.springboottemplate.common.util.AuthUtil;
import com.leoli04.springboottemplate.common.util.IpUtil;
import com.leoli04.springboottemplate.service.DemoService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

/**
 * @Description: 请求过滤器：打印请求日志
 * @Author: LeoLi04
 * @CreateDate: 2024/7/22 6:30
 * @Version: 1.0
 */
@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*", filterName = "requestFilter")
public class RequestFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

    @Autowired
    private DemoService demoService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String url = httpServletRequest.getRequestURI();
        if(StringUtils.isNotEmpty(url)){
            // option 可以插入操作日志
        }

        logger.info("RequestFilter====>userIp:{},UserId:{},Url:{},Token:{},Device-Id:{},phoneTimeZone:{}",
                IpUtil.getIpAddress(httpServletRequest), AuthUtil.currentUserId(), url, AuthUtil.accessToken(),
                AuthUtil.deviceId(), AuthUtil.phoneTimeZone());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // option 初始化需要注入的bean
        ServletContext sc = filterConfig.getServletContext();
        WebApplicationContext cxt =  WebApplicationContextUtils.getWebApplicationContext(sc);
        if (cxt != null && cxt.getBean("demoService") != null && demoService == null){
            demoService = (DemoService) cxt.getBean("demoService");
        }
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
