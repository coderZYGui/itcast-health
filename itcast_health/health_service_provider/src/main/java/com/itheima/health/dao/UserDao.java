package com.itheima.health.dao;

import com.itheima.health.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * Description: 用户Dao
 *
 * @author zygui
 * @date Created on 2020/4/7 10:03
 */
public interface UserDao {

    User findByUsername(@Param("username") String username);

}
