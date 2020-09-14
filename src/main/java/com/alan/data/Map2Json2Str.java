package com.alan.data;

import java.util.Map;

import net.sf.json.JSONObject;

public class Map2Json2Str {
    
    public static String map2Json2Str(Map<String, Object> map){
        return JSONObject.fromObject(map).toString();
    }
}
