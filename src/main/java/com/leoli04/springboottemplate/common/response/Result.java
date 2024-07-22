package com.leoli04.springboottemplate.common.response;

import io.micrometer.common.util.StringUtils;
import lombok.Data;

/**
 * @Description: 响应数据封装类
 * @Author: LeoLi04
 * @CreateDate: 2024/7/21 21:52
 * @Version: 1.0
 */
@Data
public class Result<T> {
    /**
     * 状态码
     */
    protected String code;

    /**
     * 响应信息
     */
    protected String message;

    /**
     * 返回数据
     */
    private T data;


    public Result(String code,String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ResultCodeEnum codeEnum, T data) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
        this.data = data;
    }

    public static Result success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS,data);
    }

    public static <T> Result<T> create(String code, String message, T data) {
        return new Result<>(code,message, data);
    }

    public static <T> Result<T> error(ResultCodeEnum codeEnum) {
        return new Result<>(codeEnum.getCode(), codeEnum.getMessage(),null);
    }

    public static <T> Result<T> error(String code,String message) {
        if(StringUtils.isBlank(code)){
            code = ResultCodeEnum.SYSTEM_ERROR.getCode();
        }
        return new Result<>(code, message,null);
    }
}
