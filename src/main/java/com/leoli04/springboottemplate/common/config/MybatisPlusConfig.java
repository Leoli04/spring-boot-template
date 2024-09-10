package com.leoli04.springboottemplate.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @Description: mybatis-plus配置
 * 参考地址：https://baomidou.com/introduce/
 * @Author: LeoLi04
 * @CreateDate: 2024/7/27 16:17
 * @Version: 1.0
 */
@Configuration
@Slf4j
public class MybatisPlusConfig {
    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 如果配置多个插件, 切记分页最后添加
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        // 动态表名插件
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
            // 获取参数方法
            Map<String, Object> paramMap = RequestDataHelper.getRequestData();
            if (CollectionUtils.isNotEmpty(paramMap)) {
                var tableNameParam = (String) paramMap.get("tableName");
                paramMap.forEach((k, v) -> log.info(k + "----" + v));
                if(tableNameParam.equals(tableName)){
                    // 获取传递的参数
                    String tableSuffix = (String) paramMap.get("tableSuffix");
                    if(StringUtils.isBlank(tableSuffix)){
                        return tableName;
                    }else{
                        // 组装动态表名
                        return tableName + "_" + tableSuffix;
                    }
                }
            }
            return tableName;
        });
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        return interceptor;
    }
}
