package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description: 预约设置批量导入类
 *
 * @author zygui
 * @date Created on 2020/3/30 9:43
 */
@Service
@Slf4j
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Transactional
    @Override
    public void add(List<OrderSetting> OrderSettingList) {
        log.debug(">>>>>>>预约设置列表: OrderSettingList:{}", OrderSettingList);
        // 遍历数据,插入或更新数据
        for (OrderSetting orderSetting : OrderSettingList) {
            // 判断该预约日期是否有预约
            Long count = orderSettingDao.countByOrderDate(orderSetting.getOrderDate());
            if (count > 0) {
                // 该日期下有预约数据
                orderSettingDao.updateOrderSettingByOrderDate(orderSetting);
            } else {
                orderSettingDao.add(orderSetting);
            }
        }
    }
}
