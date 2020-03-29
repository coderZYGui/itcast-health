package com.itheima.health_jobs;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;

/**
 * Description: 七牛工具类,用来定时清理图片
 *
 * @author zygui
 * @date Created on 2020/3/29 21:11
 */
public class QiniuUtils {
    public  static String accessKey = "7UA4PfHorBVdm8op3C_ionQVqHbAQxMcOZ5PnDNQ";
    public  static String secretKey = "ACI0XWYp-Pnyd7qZXskbGoXaKuzOm7EebotCGOkB";
    public  static String bucket = "zyguihealth";

    /**
     * 删除文件
     * @param fileName 服务端文件名
     */
    public static void deleteFileFromQiniu(String fileName){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

}

