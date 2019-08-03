package com.itheima.exception;

/**
 * 自定义异常：
 * 使用场景：终止已经不符合业务逻辑的代码继续执行
 */
public class HealthException extends RuntimeException {
    public HealthException(String message){
        super(message);
    }
}
