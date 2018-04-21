package com.scrawl.iot.paper.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 * Created by as on 2018/4/19.
 */
public class BeanUtil {

    public static Map<String, Object> object2Map(Object object) {
        if (null == object) {
            return new HashMap<>();
        }
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
        Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
