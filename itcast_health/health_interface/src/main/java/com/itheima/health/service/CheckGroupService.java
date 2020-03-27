package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;

/**
 * Description: 检查组业务接口
 *
 * @author zygui
 * @date 2020/3/24 17:07
 */
public interface CheckGroupService {

    /**
     * 新增检查组
     * @param checkGroup 检查组中的表单数据
     * @param checkItemIds 选择的检查项的ID列表
     */
    void add(CheckGroup checkGroup, Integer[] checkItemIds);

    /**
     * 分页查询检查组列表
     * @param currentPage 当前页码
     * @param pageSize    每页记录数
     * @param queryString 查询条件
     * @return
     */
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

}
