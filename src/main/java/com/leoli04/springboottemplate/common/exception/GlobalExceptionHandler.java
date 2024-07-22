package com.leoli04.springboottemplate.common.exception;

import com.alibaba.fastjson.JSON;
import com.leoli04.springboottemplate.common.response.Result;
import com.leoli04.springboottemplate.common.response.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Description: 统一异常处理类
 * @Author: LeoLi04
 * @CreateDate: 2024/7/7 10:56
 * @Version: 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BizException.class)
    public Result bizExceptionHandler(HttpServletRequest req, BizException e){
        log.error("发生业务异常！请求url:{},param:{},原因是：{}",req.getRequestURI(), JSON.toJSONString(req.getParameterMap()),e.getMessage());
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value =NullPointerException.class)
    public Result exceptionHandler(HttpServletRequest req, NullPointerException e){
        log.error("发生空指针异常！请求url:{},param:{}",req.getRequestURI(), JSON.toJSONString(req.getParameterMap()),e);
        return Result.error(ResultCodeEnum.NPE_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result exceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e){
        log.error("发生参数异常！请求url:{},param:{}",req.getRequestURI(), JSON.toJSONString(req.getParameterMap()),e);
        return Result.error(ResultCodeEnum.PARAM_ERROR);
    }

    @ExceptionHandler(value =Exception.class)
    public Result exceptionHandler(HttpServletRequest req, Exception e){
        log.error("未知异常！请求url:{},param:{}",req.getRequestURI(), JSON.toJSONString(req.getParameterMap()),e);
        return Result.error(ResultCodeEnum.SYSTEM_ERROR);
    }
}
