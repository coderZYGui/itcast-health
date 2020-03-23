package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.service.UserService;
import lombok.extern.slf4j.Slf4j;

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
}
