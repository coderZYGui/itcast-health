package com.itheima.health.service;

import com.itheima.health.pojo.User;

/**
 * Description:
 *
 * @author zygui
 * @date 2020/3/22 9:55
 */
public interface UserService {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密 码
     * @return
     */
    boolean login(String username,String password);

    /**
     * 根据用户名来查询用户信息
     * @param username 用户名
     * @return 用户的信息
     */
    User findUserName(String username);
}
