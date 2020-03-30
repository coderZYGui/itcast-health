package com.itheima.health.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;

/**
 * Description:
 *
 * @author zygui
 * @date Created on 2020/3/30 9:42
 */
public interface OrderSettingService {

    /**
     * 预约设置批量导入
     * @param OrderSettingList
     */
    void add(List<OrderSetting> OrderSettingList);

}
