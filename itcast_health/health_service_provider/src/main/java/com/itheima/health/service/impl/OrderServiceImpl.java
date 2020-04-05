package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.common.MessageConst;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Transactional
    @Override
    public Result addOrder(Map<String, String> map) {
        log.debug(">>>>> map:{}", map);

        // 根据预约日期, 判断是否有预约设置
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(map.get("orderDate"));
        if (orderSetting == null){
            return new Result(false, MessageConst.GET_ORDERSETTING_FAIL);
        }
        // 检查预约人数已满
        if (orderSetting.getReservations() >= orderSetting.getNumber()){
            return new Result(false, MessageConst.ORDER_FULL);
        }

        // 99(模拟) 表示 订单号
        return new Result(true, MessageConst.ORDER_SUCCESS, "99");
    }
}
