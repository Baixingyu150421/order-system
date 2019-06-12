package com.bxy.sell.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数生成工具类 生成唯一的主键
 */
public class KeyUtil {
    /**
     *  生成唯一的组件
     *  时间+随机数
     * @return
     */
    public static  synchronized String genUniqueKey(){
        Random random = new Random();
        //生成六位随机数
        Integer number = random.nextInt(900000) + 100000;
        return   System.currentTimeMillis() + String.valueOf(number);
    }
}
