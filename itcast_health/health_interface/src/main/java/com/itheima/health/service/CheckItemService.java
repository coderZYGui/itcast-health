package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

/**
 * Description:检查项业务接口
 *
 * @author zygui
 * @date 2020/3/23 11:35
 */
public interface CheckItemService {

    /**
     * 新增检查项
     * @param checkItem 检查项表单数据
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询检查项列表
     * @param queryPageBean 查询分页的数据
     * @return 分页结果的实体类
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 根据前台传的id来删除 检查项
     * @param id 检查项row中的id
     */
    void deleteById(Integer id);

    /**
     * 根据id来获取检查项
     * @param id 检查项的id
     * @return 返回检查项的数据
     */
    CheckItem findById(Integer id);

    /**
     * 更新检查项
     * @param checkItem 前台传过来的表单数据
     */
    void edit(CheckItem checkItem);
}
