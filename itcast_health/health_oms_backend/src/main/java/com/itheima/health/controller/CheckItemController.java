package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            //log.debug("CheckItemController checkItem:{}", checkItem);
            System.out.println(checkItem);
            checkItemService.add(checkItem);
            // 会返回一个JSON, 对象转JSON,在配置文件中配置了fastJson
            // 因为 @RestController = @Controller + @ResponseBody(将Controller返回的对象转为JSON数据)
            return new Result(true, MessageConst.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            log.debug("queryPageBean:{}", queryPageBean);
            return checkItemService.pageQuery(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PageResult(0L, new ArrayList());
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        log.debug("delete id:{}", id);
        try {
            checkItemService.deleteById(id);
            return new Result(true, MessageConst.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        log.debug("findById id:{}", id);
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true, MessageConst.QUERY_CHECKITEM_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody  CheckItem checkItem){
        try{
            log.debug("edit checkItem:{}",checkItem);
            // 调用Service，更新数据
            checkItemService.edit(checkItem);
            // 成功，返回编辑成功消息
            return new Result(true,MessageConst.EDIT_CHECKITEM_SUCCESS);
        }catch(Exception e){
            e.printStackTrace();
            // 失败，返回编辑失败消息
            return new Result(false,MessageConst.EDIT_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckItem> list = checkItemService.findAll();
            return new Result(true, MessageConst.QUERY_CHECKITEM_SUCCESS, list);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_CHECKITEM_FAIL);
        }
    }
}
