package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author zygui
 * @date 2020/3/22 10:10
 */
@RestController // 等于 @Controller + @ResponseBody
@RequestMapping("/web/user")
public class UserController {

    @Reference
    private UserService userService;

    // 返回的是一个Result对象(表示JSON数据的对象)
    @RequestMapping("/login")
    public Result login(String username, String password){
        try {
            if (userService.login(username, password)){
                // 会返回一个JSON, 对象转JSON,在配置文件中配置了fastJson
                return new Result(true, MessageConst.ACTION_SUCCESS, username);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new Result(false, MessageConst.ACTION_FAIL);
    }
}
