package com.itheima.controller;

import com.itheima.entity.Result;
import com.itheima.exception.HealthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    // 记录日志
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HealthException.class)
    @ResponseBody
    public Result handlerHealthException(HealthException e){
        log.error("验证失败",e);
        return new Result(false,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handlerException(Exception e){
        log.error("出错了",e);
        return new Result(false,"出错了，请联系管理员");
    }
}
