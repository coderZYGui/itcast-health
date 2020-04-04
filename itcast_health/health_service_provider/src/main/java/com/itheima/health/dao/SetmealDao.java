package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description: 套餐Dao
 *
 * @author zygui
 * @date Created on 2020/3/29 16:27
 */
public interface SetmealDao {

    /**
     * 添加套餐信息
     *
     * @param setmeal 套餐对象
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐与检查组关联关系
     *
     * @param map 套餐ID与检查组ID对于关系
     */
    void addSetmealAndCheckGroup(Map<String, Integer> map);

    Page<Setmeal> PageBean(@Param("queryString") String queryString);

    List<Setmeal> findAll();

    Setmeal findById(@Param("id") Integer id);
}
