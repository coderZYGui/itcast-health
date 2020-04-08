package com.itheima.health.dao;

import com.itheima.health.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * Description: 订单dao
 *
 * @author zygui
 * @date Created on 2020/4/5 13:45
 */
public interface OrderDao {

    /**
     * 多条件组合查询(是否已经预约过)
     * @param order (查询条件封装到对象)
     * @return
     */
    List<Order> selectByCondition(Order order);

    /**
     * 保存订单(预约记录)
     * @param order
     */
    void add(Order order);

    /**
     * 基于订单id, 获取订单详情(4个属性)
     * @param id
     * @return
     */
    Map<String, Object> findById4Detail(Integer id);

    /**
     * 获取套餐占比
     * @return
     */
    List<Map<String, Object>> findSetmealCount();
}
