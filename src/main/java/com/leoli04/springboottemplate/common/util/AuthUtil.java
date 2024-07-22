package com.leoli04.springboottemplate.common.util;

import com.leoli04.springboottemplate.common.consts.RequestConst;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Description: //todo
 * @Author: LeoLi04
 * @CreateDate: 2024/7/22 7:12
 * @Version: 1.0
 */
public class AuthUtil {

    /**
     * 获取当前登陆用户手机设置的时区
     */
    public static String phoneTimeZone() {
        String phoneTimeZone = getRequest().getHeader(RequestConst.HEADER_PHONE_TIMEZONE);
        return StringUtils.isBlank(phoneTimeZone) ? "" : phoneTimeZone;
    }

    /**
     * 获取当前登陆用户ID,未登陆用户返回0
     */
    public static Integer currentUserId() {
        String currentUserId = getRequest().getHeader(RequestConst.HEADER_USER_ID);
        return StringUtils.isBlank(currentUserId) ? null : Integer.valueOf(currentUserId);
    }

    /**
     * 获取当前登陆用户TOKEN,未登陆用户返回空字符串
     */
    public static String accessToken() {
        String accessToken = getRequest().getHeader(RequestConst.HEADER_ACCESS_TOKEN);
        return StringUtils.isBlank(accessToken) ? "" : accessToken;
    }


    /**
     * 获取当前用户设备号,未获取到返回空字符串
     */
    public static String deviceId() {
        String deviceId = getRequest().getHeader(RequestConst.HEADER_DEVICE_ID);
        return StringUtils.isBlank(deviceId) ? "" : deviceId;
    }

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
