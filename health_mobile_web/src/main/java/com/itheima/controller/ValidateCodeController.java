package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @PostMapping("send4Order.do")
    public Result send4Order(String telephone){
        Jedis jedis = jedisPool.getResource();
        //- 验证是否已经发送过了，加上前缀是为了区分业务
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        if(null != jedis.get(key)) {
            //提示注意查询,
            return new Result(false, MessageConstant.SENT_VALIDATECODE);
        }
        // - 没有发送过，生成验证码，调用短信工具发送验证码
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code + "");
            // - 把验证码存入redis中（ 提交预约时要来做验证，判断是否重复发送）
            // setex 设置的key，超时会自动删除
            // 1: key, 2: 时间长度,s, 3: value
            jedis.setex(key, 5*60, code + "");
            //- 存入redis中的验证需要设置它的有效期(过期了就会从redis中删除), 防止验证码的重复使用，占用内存
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
    }

    //手机快速登录时发送手机验证码
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        Integer code = ValidateCodeUtils.generateValidateCode(6);//生成6位数字验证码
        try {
            //发送短信
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            //验证码发送失败
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("发送的手机验证码为：" + code);
        //将生成的验证码缓存到redis
        jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone,
                5 * 60,
                code.toString());
        //验证码发送成功
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
