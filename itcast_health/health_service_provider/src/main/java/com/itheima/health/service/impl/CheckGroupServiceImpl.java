package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 检查组业务实现类
 *
 * @author zygui
 * @date 2020/3/24 17:09
 */
@Service
@Slf4j
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Transactional
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        log.debug(">>> checkItemIds:{} checkGroup:{}", checkItemIds, checkGroup);
        // 添加检查组,获取检查组id
        checkGroupDao.add(checkGroup);
        log.debug(">>> add checkgroup_id:{}", checkGroup.getId());
        // 检查组和检查项关系的中间表
        // 检查组id --- 检查项id
        //   31           28
        //   31           29
        //   31           30
        // 遍历选择的检查项列表,逐个添加到检查组检查项关系表中
        for (Integer checkItemId : checkItemIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("checkgroup_id", checkGroup.getId());
            map.put("checkitem_id", checkItemId);
            checkGroupDao.addCheckGroupAndCheckItem(map);

            /*
            // 没有添加事务时测试
            if (true){
                //log.debug("new RuntimeException");
                //throw new RuntimeException("测试事务异常抛出");
            }*/
        }
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        log.debug(">>>>> currentPage:{}, pageSize:{}, queryString:{}", currentPage, pageSize, queryString);
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        log.debug(">>>> findById:{}", id);
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        log.debug(">>>> findCheckItemIdsByCheckGroupId:{}", id);
        /*List<Integer> checkItemIds = new ArrayList<>();
        checkItemIds.add(28);
        checkItemIds.add(29);
        checkItemIds.add(30);*/
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Transactional
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
        log.debug(">>>> checkGroup:{} checkItemIds:{}", checkGroup, checkItemIds);
        // 更新检查组信息
        checkGroupDao.edit(checkGroup);
        // 删除检查组之前的关联关系
        checkGroupDao.deleteCheckItemsListByCheckGroupId(checkGroup.getId());
        // 保存新的关系
        for (Integer checkItemId : checkItemIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("checkgroup_id", checkGroup.getId());
            map.put("checkitem_id", checkItemId);
            // 添加新的关联关系
            checkGroupDao.addCheckGroupAndCheckItem(map);
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    @Transactional
    @Override
    public void deleteGroupById(Integer groupId) {
        // 根据检查组的id, 去中间表中删除它和检查项的关系
        checkGroupDao.deleteCheckItemsListByCheckGroupId(groupId);
        // 然后再删除检查组的信息
        checkGroupDao.deleteGroup(groupId);
    }
}
