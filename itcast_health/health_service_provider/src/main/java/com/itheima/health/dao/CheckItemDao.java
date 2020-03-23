package com.itheima.health.dao;

import com.itheima.health.pojo.CheckItem;

/**
 * Description:
 *
 * @author zygui
 * @date 2020/3/23 16:48
 */
public interface CheckItemDao {

    /**
     * 新增检查项
     * @param checkItem 检查数据项
     */
    void add(CheckItem checkItem);
}
