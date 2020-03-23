package com.itheima.health.service;

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
}
