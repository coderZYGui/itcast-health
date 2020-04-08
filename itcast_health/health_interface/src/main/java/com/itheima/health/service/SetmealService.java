package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * Description: 套餐业务接口
 *
 * @author zygui
 * @date Created on 2020/3/29 16:04
 */
public interface SetmealService {

    /**
     * 新增套餐
     * @param setmeal 套餐表单数据
     * @param checkGroupIds 套餐选中检查组列表
     */
    void add(Setmeal setmeal, Integer[] checkGroupIds);

    /**
     * 分页查询
     * @param queryPageBean 封装分页的信息
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    // ----------------------------下面是移动端的接口===============================

    /**
     * 查询所有的套餐
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 根据前台传过来的套餐id, 来查询该套餐
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 查询各个套餐的预约数量,并存入List中
     * @return 返回一个List集合(存放 套餐和预约套餐人数)
     */
    List<Map<String, Object>> findSetmealCount();
}
