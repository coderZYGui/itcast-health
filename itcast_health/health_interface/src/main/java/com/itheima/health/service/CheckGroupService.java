package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

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

    /**
     * 根据ID,获取检查组信息
     * @param id 检查组id
     * @return 检查组对象
     */
    CheckGroup findById(Integer id);

    /**
     * 根据前端传来的检查组id,获取该检查组中的检查项id列表
     * @param id 检查组id
     * @return 检查项id列表
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    /**
     * 编辑检查组,根据检查组id来更新
     * @param checkGroup 检查组表单数据
     * @param checkItemIds 选中的检查项ID列表
     */
    void edit(CheckGroup checkGroup, Integer[] checkItemIds);
}
