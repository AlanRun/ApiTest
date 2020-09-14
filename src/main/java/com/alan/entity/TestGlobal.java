package com.alan.entity;

import java.util.HashMap;
import java.util.Map;

public class TestGlobal {
    
    private String protocol;
    
    private String wmethod;
    
    private String url;
    
    private String body;
    
    private String description;
    
    private Map<String, ?> Headers = new HashMap<>();
    
    private Map<String, ?> cookies = new HashMap<>();
    
    private Map<String, ?> params = new HashMap<>();
    
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getWmethod() {
        return wmethod;
    }

    public void setWmethod(String wmethod) {
        this.wmethod = wmethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, ?> getHeaders() {
        return Headers;
    }

    public void setHeaders(Map<String, ?> headers) {
        Headers = headers;
    }

    public Map<String, ?> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, ?> cookies) {
        this.cookies = cookies;
    }

    public Map<String, ?> getParams() {
        return params;
    }

    public void setParams(Map<String, ?> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "TestGlobal [protocol=" + protocol + ", wmethod=" + wmethod + ", url=" + url + ", body=" + body
                + ", description=" + description + ", Headers=" + Headers + ", cookies=" + cookies + ", params="
                + params + "]";
    }
    
}
