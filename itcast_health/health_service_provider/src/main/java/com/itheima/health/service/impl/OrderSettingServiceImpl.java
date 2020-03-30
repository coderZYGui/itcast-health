package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        log.debug("getOrderSettingByMonth date:{}", date);
        List<Map> listMap = new ArrayList<>();
        // {date: 3, number:100, reservations:0}
        // date的格式: 2020-3
        String beginDate = date+"-1";
        String endDate = date+"-31";
        List<OrderSetting> orderSettingList = orderSettingDao.getOrderSettingByMonth(beginDate, endDate);
        // 把OrderSetting的date转换为某月的某一天
        for (OrderSetting orderSetting : orderSettingList) {
            Map map = new HashMap();
            map.put("date", orderSetting.getOrderDate().getDate());
            map.put("number", orderSetting.getNumber());
            map.put("reservations", orderSetting.getReservations());
            listMap.add(map);
        }
        return listMap;
    }
}
