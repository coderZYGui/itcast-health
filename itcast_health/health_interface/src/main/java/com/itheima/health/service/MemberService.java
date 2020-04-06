package com.itheima.health.service;

/**
 * Description: 会员业务接口
 *
 * @author zygui
 * @date Created on 2020/4/6 9:44
 */
public interface MemberService {

    /**
     * 基于手机号登录
     * @param telephone 手机号
     */
    void smsLogin(String telephone);
}
