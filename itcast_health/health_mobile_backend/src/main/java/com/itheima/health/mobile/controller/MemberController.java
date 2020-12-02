package com.itheima.health.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * Description: 会员控制器
 *
 * @author zygui
 * @date Created on 2020/4/6 9:53
 */
@RestController
@Slf4j
@RequestMapping("/mobile/member")
public class MemberController {

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 短信登录控制器
     * @param map
     * @return
     */
    @RequestMapping("/smsLogin")
    // 因为没有提供对应的Pojo类来接收前端传过来的手机号和验证码,所以使用map接收
    public Result smsLogin(@RequestBody Map<String, String> map){
        // 检验验证码是否正确
        try {
            // 验证验证码是否正确
            String telephone = map.get("telephone");
            String code = map.get("validateCode");
            // 从Redis中读取验证码(并判断)
            String codeInRedis = jedisPool.getResource().get(telephone + RedisConst.SENDTYPE_LOGIN);
            if (codeInRedis == null || !codeInRedis.equals(code)){
                // 验证码已经过期 或者 验证码输入不正确
                return new Result(false, MessageConst.VALIDATECODE_ERROR);
            }
            // 正确, 就完成登录逻辑
            memberService.smsLogin(telephone);
            return new Result(true, MessageConst.LOGIN_SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.ACTION_FAIL);
        }
    }
}
