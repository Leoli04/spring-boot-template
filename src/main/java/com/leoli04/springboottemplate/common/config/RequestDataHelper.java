package com.leoli04.springboottemplate.common.config;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.util.Map;

/**
 * @Description: 请求参数传递辅助类
 * @Author: LeoLi04
 * @CreateDate: 2024/9/9 18:19
 * @Version: 1.0
 */
public class RequestDataHelper {
    /**
     * 请求参数存取
     */
    private static final ThreadLocal<Map<String, Object>> REQUEST_DATA = new ThreadLocal<>();

    /**
     * 设置请求参数
     *
     * @param requestData 请求参数 MAP 对象
     */
    public static void setRequestData(Map<String, Object> requestData) {
        REQUEST_DATA.set(requestData);
    }

    /**
     * 获取请求参数
     *
     * @param param 请求参数
     * @return 请求参数 MAP 对象
     */
    public static <T> T getRequestData(String param) {
        Map<String, Object> dataMap = getRequestData();
        if (CollectionUtils.isNotEmpty(dataMap)) {
            return (T) dataMap.get(param);
        }
        return null;
    }

    /**
     * 获取请求参数
     *
     * @return 请求参数 MAP 对象
     */
    public static Map<String, Object> getRequestData() {
        return REQUEST_DATA.get();
    }

    /**
     * 清除请求参数
     */
    public static void removeRequestData() {
        REQUEST_DATA.remove();
    }
}
