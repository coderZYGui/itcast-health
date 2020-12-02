package com.itheima.health.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
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
 * @date Created on 2020/4/5 10:19
 */

@RestController
@Slf4j
@RequestMapping("/mobile/order")
public class OrderServiceController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    /**
     * 提交预约订单
     * @param map
     * @return
     */
    @RequestMapping("/submitOrder")
    public Result submitOrder(@RequestBody Map<String, String> map){

        try {
            // 获取手机号, 获取验证码
            String telephone = map.get("telephone");
            String code = map.get("validateCode");
            log.debug(">>>>>> telephone:{}, code:{}", telephone, code);
            // 验证短信
//            String codeInRedis = jedisPool.getResource().get(telephone+ RedisConst.SENDTYPE_ORDER);
//            if (codeInRedis == null || !codeInRedis.equals(code)){
//                return new Result(false, MessageConst.VALIDATECODE_ERROR);
//            }

            map.put("orderType", Order.ORDERTYPE_WEIXIN);

            // 调用service, 添加订单
            Result result = orderService.addOrder(map);
            // 发送通知
            log.debug("############# 发送短信通知预约人");
            return result;

        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.ACTION_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById4OrderDetail(Integer id){
        try {
            Map<String, Object> map = orderService.findById4OrderDetail(id);
            return new Result(true, MessageConst.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_ORDER_FAIL);
        }
    }
}
