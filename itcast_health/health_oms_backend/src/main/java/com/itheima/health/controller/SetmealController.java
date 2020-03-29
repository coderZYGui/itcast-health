package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * Description:
 *
 * @author zygui
 * @date Created on 2020/3/29 15:25
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            // 原名
            String fileName = imgFile.getOriginalFilename();
            log.debug("######getOriginalFilename:{}", fileName);
            // UUID + "_" + fileName
            String saveUploadName = UUID.randomUUID().toString().replace("-", "") + "_" + fileName;
            // 使用七牛上传
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), saveUploadName);
            // 下发到客户端: http://xxx/saveUploadName
            String filePath = QiniuUtils.qiniu_img_url_pre + saveUploadName;
            log.debug("#####filePath:{}", filePath);

            // 把文件名存入redis,基于Redis的Set集合
            jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_RESOURCES,saveUploadName);

            return new Result(true, MessageConst.PIC_UPLOAD_SUCCESS, filePath);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkGroupIds) {
        try {
            // 将表单提交的图片路径中的域名地址去掉; 因为图片的路径存在数据库中,不要域名,否则当更改域名后,很麻烦!
            log.debug("img url 此时获取的是图片的全路径:{}", setmeal.getImg());
            // 此时获取的getImg是带域名的图片地址,使用replace将域名替换为空
            String saveName = setmeal.getImg().replace(QiniuUtils.qiniu_img_url_pre, "");
            // 然后再将去掉域名的图片名,赋给 img
            setmeal.setImg(saveName);
            log.debug("img url 此时获取的是图片不带域名的路径:{}", setmeal.getImg());
            setmealService.add(setmeal, checkGroupIds);
            return new Result(true, MessageConst.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_SETMEAL_FAIL);
        }
    }
}
