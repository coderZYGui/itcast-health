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

    /**
     * 基于分页插件进行分页查询
     * @param queryString 查询条件
     * @return 根据条件查询后的数据
     */
    Page<CheckItem> selectByCondition(@Param("queryString") String queryString);

    /**
     * 根据检查项的id,查询是否有关联的数据
     * @param checkItemId 检查项id
     * @return 返回该id对应关联的数据
     */
    Long countCheckItemsById(@Param("checkItemId") Integer checkItemId);

    /**
     * 根据id来删除检查项
     * @param id 检查项id
     */
    void deleteCheckItemById(@Param("id") Integer id);
}
