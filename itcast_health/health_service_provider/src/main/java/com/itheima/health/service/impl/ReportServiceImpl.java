package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.service.ReportService;
import com.itheima.health.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Description:
 *
 * @author zygui
 * @date Created on 2020/4/7 15:13
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Integer> findMemberCountByMonthList(List<String> monthList) {
        log.debug(">>>>>> monthList:{}", monthList);
        List<Integer> memberCount = new ArrayList<>();

        // 遍历月份,查询每月之前的会员注册数量
        for (String month : monthList) {
            Integer count = memberDao.findMemberCountByMonth(month + ".31");
            memberCount.add(count);
        }

        /*
        memberCount.add(10);
        memberCount.add(15);
        memberCount.add(50);
        memberCount.add(55);
        */
        return memberCount;
    }

    @Override
    public Map<String, Object> getBusinessReportData() {
        Map<String,Object> reportData = new HashMap<>();

        // 今日,本周,本月
        Date date = DateUtils.getToday();
        Date weekDay = DateUtils.getThisWeekMonday();
        Date monthFirstDay = DateUtils.getFirstDay4ThisMonth();
        String strDate = "";
        String strWeekDay = "";
        String strMonthFirstDay = "";
        try {
            // Date转为字符串
            strDate = DateUtils.parseDate2String(date);
            strWeekDay = DateUtils.parseDate2String(weekDay);
            strMonthFirstDay = DateUtils.parseDate2String(monthFirstDay);
            log.debug(">>>>>>>>>>>: strDate:{},strWeekDay:{},strMonthFirstDay:{}", strDate, strWeekDay, strMonthFirstDay);
        } catch (Exception e){
            e.printStackTrace();
        }

        reportData.put("reportDate",strDate);
        // 会员人数相关数据
        reportData.put("todayNewMember",memberDao.totalMemberCountByDate(strDate));
        reportData.put("thisWeekNewMember",memberDao.totalMemberCountAfterDate(strWeekDay));
        reportData.put("thisMonthNewMember",memberDao.totalMemberCountAfterDate(strMonthFirstDay));
        reportData.put("totalMember",memberDao.totalMemberCount());
        // 预约人数相关数据
        reportData.put("todayOrderNumber",orderDao.totalOrderByDate(strDate));
        reportData.put("thisWeekOrderNumber",orderDao.totalOrderByAfterDate(strWeekDay));
        reportData.put("thisMonthOrderNumber",orderDao.totalOrderByAfterDate(strMonthFirstDay));
        // 到诊人数相关数据
        reportData.put("todayVisitsNumber",orderDao.totalVisitByDate(strDate));
        reportData.put("thisWeekVisitsNumber",orderDao.totalVisitByAfterDate(strWeekDay));
        reportData.put("thisMonthVisitsNumber",orderDao.totalVisitByAfterDate(strMonthFirstDay));
        // 套餐热门数据
        reportData.put("hotSetmeal",orderDao.getHotSetmeal());
        return reportData;
    }

}
