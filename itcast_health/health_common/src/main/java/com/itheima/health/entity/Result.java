package com.itheima.health.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 返回结果实体类(Controller返回一个JSON到前端)
 *
 * @author zygui
 * @date 2020/3/22 9:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {
    private boolean flag;//执行结果，true为执行成功 false为执行失败
    private String message;//返回结果信息
    private Object data;//返回数据

    public Result(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }
}
