package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description: 报表控制器
 *
 * @author zygui
 * @date Created on 2020/4/7 15:15
 */
@RestController
@Slf4j
@RequestMapping("/report")
public class ReportController {

    @Reference
    private ReportService reportService;

    /**
     * 获取前12个月,每月会员累计注册量
     * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        try {

            // 返回到12个月之前的日期
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -12);
            // 构建月份列表
            List<String> monthList = new ArrayList<>();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM");
            for (int i = 0; i != 12; i++){
                calendar.add(Calendar.MONTH, 1);
                monthList.add(simpleDateFormat.format(calendar.getTime()));
            }

            /*
            monthList.add("2019.07");
            monthList.add("2019.06");
            monthList.add("2019.05");
            monthList.add("2019.04");
            */
            // 调用Service, 获取会员统计
            List<Integer> memberCount = reportService.findMemberCountByMonthList(monthList);
            // 返回数据
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("months", monthList);
            resultMap.put("memberCount", memberCount);
            return new Result(true, MessageConst.ACTION_SUCCESS, resultMap);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.ACTION_FAIL);
        }
    }
}
