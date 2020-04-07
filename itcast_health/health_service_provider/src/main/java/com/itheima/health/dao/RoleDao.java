package com.itheima.health.dao;

import com.itheima.health.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * Description: 角色Dao
 *
 * @author zygui
 * @date Created on 2020/4/7 10:07
 */
public interface RoleDao {

    Set<Role> findByUserId(@Param("userId") Integer userId);
}
