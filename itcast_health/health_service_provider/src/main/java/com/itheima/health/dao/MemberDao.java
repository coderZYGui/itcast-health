package com.itheima.health.dao;

import com.itheima.health.pojo.Member;
import org.apache.ibatis.annotations.Param;

/**
 * Description: 会员dao
 *
 * @author zygui
 * @date Created on 2020/4/5 11:18
 */
public interface MemberDao {

    /**
     * 根据手机号,判断是否是会员
     * @param telephone
     * @return
     */
    Member findByTelephone(@Param("telephone") String telephone);

    /**
     * 添加会员
     * @param member
     */
    void add(Member member);

}
