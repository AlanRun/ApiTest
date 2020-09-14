package com.alan.data;

import java.util.HashMap;
import java.util.Map;

public class String2Map {

    public static Map<String, Object> covertString2Map(String params, String regex) {
        Map<String, Object> paramsMp = null;
        if (params != null) {
            paramsMp = new HashMap<>();
            String[] strp = params.split(regex);
            for (int i = 0; i < strp.length; i++) {
                String singleparms = strp[i];
                String[] key_values = singleparms.split("=");
                paramsMp.put(key_values[0], key_values[1]);
            }
        }
        return paramsMp;
    }

    public static Map<String, Object> covertString2Map(String params) {
        return covertString2Map(params, ";");
    }
}
