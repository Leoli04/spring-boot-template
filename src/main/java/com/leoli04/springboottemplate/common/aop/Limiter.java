package com.leoli04.springboottemplate.common.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 在api上限流
 * @Author: LeoLi04
 * @CreateDate: 2024/8/25 16:27
 * @Version: 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Limiter {

    /**
     * 默认每秒产生10个令牌
     * @return
     */
    double LimitNum() default  10;
}
