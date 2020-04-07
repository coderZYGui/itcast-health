package com.itheima.health.dao;

import com.itheima.health.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * Description: 权限Dao
 *
 * @author zygui
 * @date Created on 2020/4/7 10:17
 */
public interface PermissionDao {

    /**
     *
     * @param roleId
     * @return
     */
    Set<Permission> findByRoleId(@Param("roleId") Integer roleId);
}
