package com.ruixun.tracking.common.utils;

import com.ruixun.tracking.entity.Card;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Program: tracking
 * <p>
 * Description:
 *
 * @Date: 2020-03-28 07:03
 **/
public class MapUtil {

    private static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
//            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {

            return null;
        }
    }


    public static Map ObjectParseMap(Object o) {
        Map map = new HashMap();
        String[] filedName = getFiledName(o);
        for (int i = 0; i < filedName.length; i++) {
            Object fieldValueByName = getFieldValueByName(filedName[i], o);
            if (fieldValueByName != null)
                map.put(filedName[i], fieldValueByName);
        }
        return map;
    }

    public static Map kickKey(String key, Map map) {
        map.remove(key);


        return map;
    }
}
