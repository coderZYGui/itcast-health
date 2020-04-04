package com.itheima.health.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: 套餐控制器(移动端)
 *
 * @author zygui
 * @date Created on 2020/4/3 14:58
 */

@RestController
@Slf4j
@RequestMapping("/mobile/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getSetmealList")
    public Result getSetmealList(){
        try {
            List<Setmeal> setmealList = setmealService.findAll();
            return new Result(true, MessageConst.QUERY_SETMEALLIST_SUCCESS, setmealList);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_SETMEALLIST_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConst.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_SETMEAL_FAIL);
        }
    }
}
