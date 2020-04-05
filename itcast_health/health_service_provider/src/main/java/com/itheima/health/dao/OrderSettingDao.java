package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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

    /**
     * 根据日期来查询预约数据
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 根据预约日期, 查询是否有预约设置
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(@Param("orderDate") String orderDate);
}
