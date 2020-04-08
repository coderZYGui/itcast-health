package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.common.RedisConst;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author zygui
 * @date Created on 2020/3/29 16:06
 */
@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private OrderDao orderDao;

    @Transactional
    @Override
    public void add(Setmeal setmeal, Integer[] checkGroupIds) {
        log.debug(">>>>> checkGroupIds:{}, setmeal:{}", checkGroupIds, setmeal);
        // 保存套餐数据
        setmealDao.add(setmeal);
        // 保存套餐与检查组对应关系
        for (Integer checkGoupId : checkGroupIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("setmeal_id", setmeal.getId());
            map.put("checkgroup_id", checkGoupId);
            setmealDao.addSetmealAndCheckGroup(map);
        }

        // 当添加套餐的时候,将图片保存到Redis中
        jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        // 分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 调用dao获取Page
        Page<Setmeal> pages = setmealDao.PageBean(queryPageBean.getQueryString());
        // 构建PageResult并返回
        return new PageResult(pages.getTotal(), pages.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        log.debug("根据id为的套餐信息:{}"+id);
        return setmealDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {

        List<Map<String, Object>> mapList = orderDao.findSetmealCount();

        /*
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "套餐1");
        map1.put("value", 10);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "套餐2");
        map2.put("value", 20);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "套餐3");
        map3.put("value", 30);

        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map3);
        */

        return mapList;
    }
}
