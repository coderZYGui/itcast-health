package com.itheima.health.service;

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
}
