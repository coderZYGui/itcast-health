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
            Map map = new HashMap<>();
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
}
