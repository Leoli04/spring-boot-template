package com.leoli04.springboottemplate.common.exception;

import com.leoli04.springboottemplate.common.response.ResultCodeEnum;
import lombok.Data;

/**
 * @Description: 业务异常
 * @Author: LeoLi04
 * @CreateDate: 2024/7/21 21:41
 * @Version: 1.0
 */
@Data
public class BizException extends RuntimeException {

    private String code;
    private String message;

    public BizException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BizException(ResultCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }
}
