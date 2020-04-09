package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.ReportService;
import com.itheima.health.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
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

    @Reference
    private SetmealService setmealService;

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

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        try {
            // 通过service获取各个套餐的预约数量
            List<Map<String, Object>> mapList = setmealService.findSetmealCount();
            // 返回数据
            // 封装套餐名称列表
            List<String> setmealNames = new ArrayList<>();
            // 循环遍历List,取出map中的name值, 存入setmealNames
            for (Map<String, Object> map : mapList) {
                setmealNames.add(map.get("name").toString());
            }

            // 将 mapList 和 setmealNames一起返回给前端
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("setmealNames", setmealNames);
            resultMap.put("setmealCount", mapList);
            return new Result(true, MessageConst.ACTION_SUCCESS, resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.ACTION_FAIL);
        }
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String, Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConst.ACTION_SUCCESS, map);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.ACTION_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletResponse response){
        try {
            // 通过service获取元数据
            Map<String, Object> reportData = reportService.getBusinessReportData();
            log.debug(">>>>>> reportData:{}", reportData);
            // 把数据写入excel
            // 先读取excel
            InputStream inputStream = this.getClass().getResourceAsStream("/report_template.xlsx");
            // 构建excel对象
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            // 读取sheet
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            // 读取Row
            XSSFRow row = sheet.getRow(2);

            // ---------------------------------将运营数据都保存到excel对象中-------------------------------------------
            // 读取cell
            // 设置报表日期
            row.getCell(5).setCellValue(reportData.get("reportDate").toString());

            // 获取会员相关数据
            Integer todayNewMember = (Integer)reportData.get("todayNewMember");
            Integer thisWeekNewMember = (Integer)reportData.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer)reportData.get("thisMonthNewMember");
            Integer totalMember = (Integer)reportData.get("totalMember");
            // 获取预约相关数据
            Integer todayOrderNumber = (Integer)reportData.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer)reportData.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer)reportData.get("thisMonthOrderNumber");
            // 获取到诊相关数据
            Integer todayVisitsNumber = (Integer)reportData.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer)reportData.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer)reportData.get("thisMonthVisitsNumber");
            // 获取新增会员及会员总数行
            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);

            // 获取本周新增会员及本月新增会员行
            row = sheet.getRow(5);

            row.getCell(5).setCellValue(thisWeekNewMember);
            row.getCell(7).setCellValue(thisMonthNewMember);

            // 获取今日预约及到诊数行
            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);
            row.getCell(7).setCellValue(todayVisitsNumber);

            // 获取本周预约及本周到诊行
            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            row.getCell(7).setCellValue(thisWeekVisitsNumber);

            // 获取本月预约及本月到诊行
            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            row.getCell(7).setCellValue(thisMonthVisitsNumber);

            // 设置热门套餐
            int rowNum = 12;
            List<Map<String, Object>> hotSetmealList = (List<Map<String, Object>>) reportData.get("hotSetmeal");
            for (Map<String, Object> map : hotSetmealList) {
                String name = (String) map.get("name");
                Long setmealCount = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum);
                row.getCell(4).setCellValue(name);
                row.getCell(5).setCellValue(setmealCount);
                row.getCell(6).setCellValue(proportion.doubleValue());
                rowNum++;
            }

            // ----------------------------------------------------------------------------

            // 通过流下载excel
            // 获取服务器到客户端的响应流(使用HttpServletResponse)
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition","attachment;filename="+reportData+"_report.xlsx");
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            xssfWorkbook.close();
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.ACTION_FAIL);
        }
    }
}
