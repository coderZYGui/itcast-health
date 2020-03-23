package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

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

    Page<CheckItem> selectByCondition(@Param("queryString") String queryString);
}
