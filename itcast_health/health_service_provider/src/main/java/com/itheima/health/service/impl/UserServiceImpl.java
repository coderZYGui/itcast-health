package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.dao.UserDao;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Description:
 *
 * @author zygui
 * @date 2020/3/22 9:56
 */
@Service // 扫描是否被dubbo注册
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public boolean login(String username, String password) {
        log.debug("u: {}, p: {}", username, password);
        if ("admin".equals(username) && "123".equals(password)){
            return true;
        }
        return false;
    }

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findUserName(String username) {
        // 根据用户名获取用户基本信息
        User user = userDao.findByUsername(username);
        // 根据用户id获取角色信息
        Set<Role> roleSet = roleDao.findByUserId(user.getId());
        user.setRoles(roleSet);
        // 根据角色id,获取权限集合
        for (Role role : roleSet) {
            // 通过遍历角色,逐一获取每个角色的权限集合
            Set<Permission> permissionSet = permissionDao.findByRoleId(role.getId());
            role.setPermissions(permissionSet);
        }
        return user;
    }
}
