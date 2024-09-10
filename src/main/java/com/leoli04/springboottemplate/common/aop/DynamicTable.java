package com.leoli04.springboottemplate.common.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 分表注解
 * @Author: LeoLi04
 * @CreateDate: 2024/9/10 9:13
 * @Version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DynamicTable {
    /**
     * 需要分割的表名
     * @return
     */
    String tableName();

    /**
     * 后缀key
     * @return
     */
    String separateKey();
}
