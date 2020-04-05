package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * Description: 订单控制器
 *
 * @author zygui
 * @date Created on 2020/4/4 22:01
 */
@RestController
@Slf4j
@RequestMapping("/mobile/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map<String, String> map){
        try {

            // 从前端获取手机号和验证码(验证用户输入的是否正确)
            String telephone = map.get("telephone");
            String code = map.get("validateCode");
            log.debug("#### telephone:{}, code:{}", telephone, code);

            // 验证短信验证码(从Redis中的验证码作对比,看是否过期或者填错)
            String codeInReids = jedisPool.getResource().get(telephone + RedisConst.SENDTYPE_ORDER);
            if (codeInReids == null || !codeInReids.equals(code)){
                return new Result(false, MessageConst.VALIDATECODE_ERROR);
            }

            // 填的成功, 调用service
            Result result = orderService.addOrder(map);

            // 发送通知
            log.debug(">>>>>>>>> 发送短信通知预约人....");

            return result;

        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.ACTION_FAIL);
        }
    }
}
