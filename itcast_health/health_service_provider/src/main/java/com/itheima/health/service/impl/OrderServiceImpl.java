package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.common.MessageConst;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderService;
import com.itheima.health.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description: (预约)订单业务实现类
 *
 * @author zygui
 * @date Created on 2020/4/4 21:52
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    @Transactional
    @Override
    public Result addOrder(Map<String, String> map) {
        log.debug(">>>>> map:{}", map);
        Date orderDate = null;
        try {
            orderDate = DateUtils.parseString2Date(map.get("orderDate"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 根据预约日期, 判断是否有预约设置
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(map.get("orderDate"));
        if (orderSetting == null) {
            return new Result(false, MessageConst.GET_ORDERSETTING_FAIL);
        }
        // 检查预约人数已满
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            return new Result(false, MessageConst.ORDER_FULL);
        }

        // 检查是否是会员
        Member member = memberDao.findByTelephone(map.get("telephone"));
        if (member == null) {
            // 不是会员, 进行会员注册
            member = new Member();
            // 给会员赋值(前端用户填写的)
            member.setName(map.get("name"));
            member.setSex(map.get("sex"));
            member.setIdCard(map.get("idCard"));
            member.setPhoneNumber(map.get("telephone"));
            member.setRegTime(new Date());
            memberDao.add(member);
        } else {
            // 基于条件组合查询, 查询该用户同一天是否有预约了
            Order order = new Order();
            // 验证会员的id
            order.setMemberId(member.getId());
            // 验证会员再某一天的预约时间; 将前台预约输入的日期,转为日期对象,设置给预约订单中的日期
            try {
                order.setOrderDate(orderDate);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, "预约日期不正确!");
            }

            // 根据 用户的id,和其预约的日期. 查询是否在这个日期已经预约过
            List<Order> orders = orderDao.selectByCondition(order);
            if (orders != null && orders.size() > 0) {
                return new Result(false, MessageConst.HAS_ORDERED);
            }
        }

        // 保存订单
        Order order = new Order();
        order.setOrderDate(orderDate);
        order.setMemberId(member.getId());
        order.setSetmealId(Integer.parseInt(map.get("setmealId")));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(map.get("orderType"));
        orderDao.add(order);

        // 更新预约设置
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationByOrderDate(orderSetting);

        // 99(模拟) 表示 订单号
        return new Result(true, MessageConst.ORDER_SUCCESS, order.getId()+"");
    }
}
