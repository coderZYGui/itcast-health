package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {
        try {
            checkGroupService.add(checkGroup, checkItemIds);
            return new Result(true, MessageConst.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = null;
        try {
            pageResult = checkGroupService.pageQuery(queryPageBean.getCurrentPage(),
                    queryPageBean.getPageSize(),
                    queryPageBean.getQueryString());

        } catch (Exception e) {
            e.printStackTrace();
            pageResult = new PageResult(0L, new ArrayList());
        }
        return pageResult;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true, MessageConst.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        try {
            List<Integer> checkItemIdsByCheckGroupId = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true, MessageConst.ACTION_SUCCESS, checkItemIdsByCheckGroupId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ACTION_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds){
        try {
            checkGroupService.edit(checkGroup, checkItemIds);
            return new Result(true, MessageConst.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.EDIT_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckGroup> checkGroups = checkGroupService.findAll();
            return new Result(true, MessageConst.QUERY_CHECKGROUP_SUCCESS, checkGroups);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_CHECKGROUP_FAIL);
        }
    }
}
