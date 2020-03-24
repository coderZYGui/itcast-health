package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author zygui
 * @date 2020/3/24 17:15
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds){
        try {
            checkGroupService.add(checkGroup, checkItemIds);
            return new Result(true, MessageConst.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_CHECKGROUP_FAIL);
        }
    }
}
