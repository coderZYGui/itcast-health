package com.itheima.health.dao;

import com.itheima.health.pojo.CheckGroup;

import java.util.Map;

/**
 * Description:
 *
 * @author zygui
 * @date 2020/3/24 17:57
 */
public interface CheckGroupDao {

    /**
     * 添加检查组
     * @param checkGroup 前台传过来的检查组信息对象
     */
    void add(CheckGroup checkGroup);

    /**
     * 新增检查组和检查项的关系(多对多形成的中间表)
     * K - V
     * @param map
     */
    void addCheckGroupAndCheckItem(Map map);
}
