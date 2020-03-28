package com.ruixun.tracking.common.utils;



/**
 * @author suitao
 * @description
 */
public class JudgeEmpty {
    /*判空类的判空方法 为空返回true 不空返回false*/
    public static boolean isEmpty(Object object){
        if (object==null||object.toString()==""){
            return true;
        }
        return false;
    }
}
