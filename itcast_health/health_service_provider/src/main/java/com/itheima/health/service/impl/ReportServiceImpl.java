package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
}
