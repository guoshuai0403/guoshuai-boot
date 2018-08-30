package com.gs.common.util.map;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * map 工具类
 * Created by gs on 2018/1/24.
 */
public class MapUtil {

    /**
     * 对象转map
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null){
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }
}
