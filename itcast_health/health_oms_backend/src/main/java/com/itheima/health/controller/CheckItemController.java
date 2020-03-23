package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务的消费者
 * Description: 检查项控制器
 *
 * @author zygui
 * @date 2020/3/23 12:04
 */
@RestController
@Slf4j
@RequestMapping("/checkitem")
public class CheckItemController {
    // 远程调用Service
    @Reference
    private CheckItemService checkItemService;

    @RequestMapping("/add")
    // @RequestBody是将前台传过来的JSON数据转为Java对象
    // 用来接收表单的数据
    public Result add(@RequestBody CheckItem checkItem){
        try {
            //log.debug("CheckItemController checkItem:{}", checkItem);
            System.out.println(checkItem);
            checkItemService.add(checkItem);
            return new Result(true, MessageConst.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_CHECKITEM_FAIL);
        }
    }
}
