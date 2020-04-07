package com.itheima.health.service;

import java.util.List;

/**
 * Description:
 *
 * @author zygui
 * @date Created on 2020/4/7 15:11
 */
public interface ReportService {


    List<Integer> findMemberCountByMonthList(List<String> monthList);
}
