package com.leoli04.springboottemplate.common.validator;

import jakarta.validation.Payload;

/**
 * @Description: 是否为手机
 * @Author: LeoLi04
 * @CreateDate: 2024/7/28 0:08
 * @Version: 1.0
 */
public @interface IsMobile {

    boolean required() default true;

    String message() default "手机号码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
