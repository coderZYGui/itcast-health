package com.itheima.health_jobs;

import com.itheima.health.common.RedisConst;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

/**
 * Description: 任务类
 *
 * @author zygui
 * @date Created on 2020/3/29 20:28
 */
public class ClearJob {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 清除图片任务
     */
    public void clearImageJob(){
        System.out.println("ClearJob.clearImageJob");

        // 计算redis中两个集合的差值，获取垃圾图片名称
        Set<String> stringSet = jedisPool.getResource().sdiff(RedisConst.SETMEAL_PIC_RESOURCES, RedisConst.SETMEAL_PIC_DB_RESOURCES);
        Iterator<String> iterator = stringSet.iterator();
        while (iterator.hasNext()){
            String imgName = iterator.next();
            System.out.println("需要删除的图片:" + imgName);

            //删除图片服务器中的图片文件
            QiniuUtils.deleteFileFromQiniu(imgName);

            //删除redis中的数据
            jedisPool.getResource().srem(RedisConst.SETMEAL_PIC_RESOURCES,imgName);
        }
    }
}
