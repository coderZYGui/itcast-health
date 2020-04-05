package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Description: 订单业务实现类
 *
 * @author zygui
 * @date Created on 2020/4/4 21:52
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Transactional
    @Override
    public Result addOrder(Map<String, String> map) {
        log.debug(">>>>> map:{}", map);
        // 99(模拟) 表示 订单号
        return new Result(true, MessageConst.ORDER_SUCCESS, "99");
    }
}
