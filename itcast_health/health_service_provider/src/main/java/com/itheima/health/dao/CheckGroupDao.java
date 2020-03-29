package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
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

    /**
     * 根据查询条件来对检查组进行分页
     * @param queryString 查询条件
     * @return
     */
    Page<CheckGroup> selectByCondition(@Param("queryString") String queryString);

    /**
     * 根据ID,获取检查组信息
     * @param id 检查组id
     * @return 检查组对象
     */
    CheckGroup findById(@Param("id") Integer id);

    /**
     * 根据前端传来的检查组id,获取该检查组中的检查项id列表
     * @param id 检查组id
     * @return 检查项id列表
     */
    List<Integer> findCheckItemIdsByCheckGroupId(@Param("id") Integer id);

    /**
     * 更新检查组的基本信息
     * @param checkGroup 检查组中的表单数据
     */
    void edit(CheckGroup checkGroup);

    /**
     * 根据给定的检查组id,来删除该检查组中的所有检查项
     * @param checkGroupId
     */
    void deleteCheckItemsListByCheckGroupId(@Param("checkGroupId") Integer checkGroupId);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}
