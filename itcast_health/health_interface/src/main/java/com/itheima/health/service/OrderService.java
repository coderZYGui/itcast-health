package com.itheima.health.service;

import com.itheima.health.entity.Result;

import java.util.Map;

/**
 * Description: 订单业务接口
 *
 * @author zygui
 * @date Created on 2020/4/4 21:50
 */
public interface OrderService {

    /**
     * 添加订单
     * @param map 没有对应的pojo类,用map
     * @return
     */
    Result addOrder(Map<String, String> map);

    /**
     * 根据订单id, 获取订单的信息
     * @param id
     * @return 返回前端需要的数据
     */
    Map<String, Object> findById4OrderDetail(Integer id);
}
