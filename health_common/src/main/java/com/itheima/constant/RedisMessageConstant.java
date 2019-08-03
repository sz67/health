package com.itheima.constant;

/**
 * @ClassName RedisMessageConstant
 * @Description TODO
 * @Author
 * @Date 2019/7/26 10:13
 * @Version 1.0
 **/
public interface RedisMessageConstant {
    static final String SENDTYPE_ORDER = "001";//用于缓存体检预约时发送的验证码
    static final String SENDTYPE_LOGIN = "002";//用于缓存手机号快速登录时发送的验证码
    static final String SENDTYPE_GETPWD = "003";//用于缓存找回密码时发送的验证码
}
