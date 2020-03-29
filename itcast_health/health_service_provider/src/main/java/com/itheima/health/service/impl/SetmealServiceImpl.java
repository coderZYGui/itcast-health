package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.common.RedisConst;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
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
}
