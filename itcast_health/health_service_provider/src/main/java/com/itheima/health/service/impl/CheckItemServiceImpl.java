package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 *
 * @author zygui
 * @date 2020/3/23 16:41
 */
@Service
@Slf4j
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    // 增删改把事务加上
    @Transactional
    @Override
    public void add(CheckItem checkItem) {
        log.debug("checkitem: {}", checkItem);
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        log.debug("queryPageBean: {}", queryPageBean);
        // 将前台传过来的查询分页的对象的查询条件属性赋给queryString, 根据查询条件来查询分页的数据
        // 使用分页插件进行动态分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 通过动态limit,实现分页效果
        Page<CheckItem> pageData = checkItemDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(pageData.getTotal(), pageData.getResult());
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById {}", id);
        // 判断删除的检查项id是否有关联数据
        Long count = checkItemDao.countCheckItemsById(id);
        if (count > 0)
            throw new RuntimeException("该检查项有关联数据,不能删除!");
        checkItemDao.deleteCheckItemById(id);
    }

    @Override
    public CheckItem findById(Integer id) {
        log.debug("findById {}", id);
        return checkItemDao.findById(id);
    }

    @Transactional
    @Override
    public void edit(CheckItem checkItem) {
        log.debug("edit checkItem {}", checkItem);
        checkItemDao.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
