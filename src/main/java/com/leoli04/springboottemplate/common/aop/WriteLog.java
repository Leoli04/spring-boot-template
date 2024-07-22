package com.leoli04.springboottemplate.common.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 请求应答日志打印注解
 * PS1：请求应答会通过JSON转换，请勿在出入参有流数据时使用
 * PS2：请求应答数据量比较大的情况，尽量避免使用，否则会打印很多无意义日志，可自行打印核心方便调试的数据
 * @Author: LeoLi04
 * @CreateDate: 2024/7/22 7:22
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WriteLog {
}
