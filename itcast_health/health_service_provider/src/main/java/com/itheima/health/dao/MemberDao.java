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

    /**
     * 查询该月份之前的所有会员数(包含这个月的)
     * @param month
     * @return 会员数量
     */
    Integer findMemberCountByMonth(@Param("month") String month);

    /**
     * 统计会员总数量
     * @return
     */
    Integer totalMemberCount();

    /**
     * 统计某一日期会员注册数量
     * @return
     */
    Integer totalMemberCountByDate(@Param("date") String date);

    /**
     * 统计某一日期之后(到今日)会员注册数量
     * @return
     */
    Integer totalMemberCountAfterDate(@Param("date") String date);

}
