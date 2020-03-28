package com.ruixun.tracking.common.utils;


/**
 * Program: Novel
 * <p>
 * Description:
 *
 * @Date: 2020-02-13 14:43
 **/
public class ResultResponseUtil {
    public static Result ok() {         //成功
        return new Result(200, null, null);
    }

    public static Result error() {      //异常
        return new Result(500, null, null);
    }

    public static Result unLogin() {   //未登陆
        return new Result(501, null, null);
    }
}
