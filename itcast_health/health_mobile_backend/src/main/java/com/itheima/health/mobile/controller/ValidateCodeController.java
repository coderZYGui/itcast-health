package com.itheima.health.mobile.controller;

import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.Result;
import com.itheima.health.mobile.utils.SMSUtils;
import com.itheima.health.mobile.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * Description: 验证码控制器
 *
 * @author zygui
 * @date Created on 2020/4/4 16:27
 */

@RestController
@Slf4j
@RequestMapping("/mobile/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        try {
            // 生成验证码
            String validateCode = ValidateCodeUtils.generateValidateCode(4).toString();
            log.debug(">>>>>>>>>> telephone:{}, validate:{}", telephone, validateCode);
            // 调用三方发送验证码
            SMSUtils.sendShortMessage(telephone, validateCode);
            // 将验证码存入Redis, 并设置有效期为5分钟
            jedisPool.getResource().setex(telephone+RedisConst.SENDTYPE_ORDER, 5*60, validateCode);
            return new Result(true, MessageConst.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.VALIDATECODE_ERROR);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        try {
            // 生成验证码
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            // 第三方发送验证码
            SMSUtils.sendShortMessage(telephone, code);
            log.debug("code:{}", code);
            // 将验证码存入redis
            jedisPool.getResource().setex(telephone+RedisConst.SENDTYPE_LOGIN, 5*60, code);
            return new Result(true, MessageConst.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.SEND_VALIDATECODE_FAIL);
        }
    }
}
