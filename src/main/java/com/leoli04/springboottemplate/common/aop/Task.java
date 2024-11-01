package com.leoli04.springboottemplate.common.aop;

import com.leoli04.springboottemplate.common.consts.TaskConst;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 任务注解
 * @Author: LeoLi04
 * @CreateDate: 2024/10/23 17:07
 * @Version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Task {

    TaskConst.TaskTypeEnum taskType();
}
