package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;

/**
 * Description: 预约设置dao
 *
 * @author zygui
 * @date Created on 2020/3/30 11:09
 */
public interface OrderSettingDao {

    /**
     * 添加预约设置
     * @param orderSetting 预约设置数据
     */
    void add(OrderSetting orderSetting);

    /**
     * 基于预约日期更新预约设置
     * @param orderSetting 预约设置数据
     */
    void updateOrderSettingByOrderDate(OrderSetting orderSetting);

    /**
     * 统计某一日期下的数据
     * @param orderDate
     * @return
     */
    Long countByOrderDate(Date orderDate);
}
