package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description: 预约设置控制器
 *
 * @author zygui
 * @date Created on 2020/3/30 9:46
 */
@RestController
@RequestMapping("/ordersetting")
@Slf4j
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile multipartFile){
        try {
            // 测试是否拿到文件
            String fileName = multipartFile.getOriginalFilename();
            log.debug(">>>>>Excel文件名字: fileName:{}", fileName);

            // 从excel中读取数据,遍历数据,封装成对象
            List<String[]> list = POIUtils.readExcel(multipartFile);
            // 把excel数据转换为List<OrderSetting>
            List<OrderSetting> orderSettingList = new ArrayList<>();
            for (String[] row : list) {
                log.debug(">>>>>每一行显示的内容: row[0]:{}, row[1]:{}", row[0], row[1]);
                OrderSetting orderSetting = new OrderSetting();
                orderSetting.setOrderDate(new Date(row[0]));
                orderSetting.setNumber(Integer.parseInt(row[1]));
                orderSettingList.add(orderSetting);
            }
            // orderSettingService拿到的已经是封装好的数据了
            orderSettingService.add(orderSettingList);
            return new Result(true, MessageConst.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map> mapList = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true, MessageConst.GET_ORDERSETTING_SUCCESS, mapList);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.GET_ORDERSETTING_FAIL);
        }
    }
}
