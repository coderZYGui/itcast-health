package com.itheima.health.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取某年某月预约设置列表
     * @param date yyyy-mm
     * @return
     */
    List<Map> getOrderSettingByMonth(String date);

}
