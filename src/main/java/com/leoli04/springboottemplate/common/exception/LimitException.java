package com.leoli04.springboottemplate.common.exception;

import lombok.Getter;

/**
 * @Description: 限流异常
 * @Author: LeoLi04
 * @CreateDate: 2024/8/25 16:36
 * @Version: 1.0
 */
@Getter
public class LimitException extends RuntimeException{
    private String code;
    private String message;
    public LimitException(String code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
