package com.alan.data;

import java.util.Map;

public class Cookies2Headers {

    public static Map<String, Object> covertCookies2Headers(Map<String, String>cookies, Map<String, Object> headers){
        
        StringBuffer cookieStr = new StringBuffer();
        for (Map.Entry<String, String> cookie : cookies.entrySet()) {
            cookieStr.append(cookie.getKey()+"="+cookie.getValue()).append("; ");
        }
        headers.put("Cookie", cookieStr.toString());
        
        return headers;
    }
}
