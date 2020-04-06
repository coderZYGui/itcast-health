package com.itheima.health.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 测试控制器
 *
 * @author zygui
 * @date Created on 2020/4/6 19:50
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PreAuthorize("hasAuthority('add')")
    @RequestMapping("/addData")
    public String addData(){
        return "add ok";
    }

    @PreAuthorize("hasAuthority('update')")
    @RequestMapping("/updateData")
    public String updateData(){
        return "update ok";
    }

    @PreAuthorize("hasAuthority('find')")
    @RequestMapping("/findData")
    public String findData(){
        return "find ok";
    }

    @PreAuthorize("hasAuthority('delete')")
    @RequestMapping("/deleteData")
    public String deleteData(){
        return "delete ok";
    }
}
