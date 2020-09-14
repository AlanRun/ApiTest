package com.alan.utils;

import com.alan.entity.TestGlobal;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.alan.variable.ComVariable.*;

public class HttpUtils {
    
    private static Logger logger = Logger.getLogger(HttpUtils.class);
    private static Map<String, Object> Cookies = new HashMap<>();
    private static Map<String, Object> Headers = new HashMap<>();
    private static RequestSpecification requestSpecifcation;
    private static Response response;
    private static String baseURL;

    public static void main(String [] args){
        
    }

    public static Response httpRequest(TestGlobal testGlobal){
        
        // 判断请求头部参数
        if (testGlobal.getHeaders().isEmpty()) {
            requestSpecifcation = given();
        } else {
            logger.info("[Headers]=" + testGlobal.getHeaders());
            requestSpecifcation = given().headers(testGlobal.getHeaders());
        }
        
        // 设置请求的URL不转义
        requestSpecifcation = requestSpecifcation.urlEncodingEnabled(false).log().all();
        
        // 设置cookies
        if (!testGlobal.getCookies().isEmpty()) {
            logger.info("[Cookies]=" + testGlobal.getCookies());
            requestSpecifcation = requestSpecifcation.cookies(testGlobal.getCookies());
        }
        
        // 判断是否HTTPS加密
        if (testGlobal.getProtocol().equalsIgnoreCase("https")) {
            requestSpecifcation = requestSpecifcation.relaxedHTTPSValidation();
        }
        
        if (testGlobal.getWmethod().equalsIgnoreCase("get")) {
            try {
                logger.info("[GetUrl]=" + testGlobal.getUrl());
                if (!testGlobal.getParams().isEmpty()) {
                    logger.info("[Params]=" + testGlobal.getParams());
                    
                    response = requestSpecifcation.
                            params(testGlobal.getParams()).
                            get(testGlobal.getUrl());
                } else {
                    response = requestSpecifcation.
                            get(testGlobal.getUrl());
                }
                
            } catch (Exception e) {
                logger.error("[Error]=" + e.getMessage());
            }
            
        } else {
            if (testGlobal.getBody() == null) {
                try {
                    logger.info("[PostUrl]=" + testGlobal.getUrl());
                    logger.info("[Params]=" + testGlobal.getParams());
                    response = requestSpecifcation.
                            contentType(ContentType_Form + ";" + Charset_UTF8).
                            params(testGlobal.getParams()).
                            post(testGlobal.getUrl());
                } catch (Exception e) {
                    logger.error("[Error]=" + e.getMessage());
                }
                
            } else {
                try {
                    logger.info("[PostUrl]=" + testGlobal.getUrl());
                    logger.info("[PostBody]=" + testGlobal.getBody());
                    
                    response = requestSpecifcation.
                        contentType(ContentType_Json + ";" + Charset_UTF8).
                        body(testGlobal.getBody()).
                        post(testGlobal.getUrl());
                } catch (Exception e) {
                    logger.error("[Error]=" + e.getMessage());
                }
            }
        }
        
        logger.info("[response]=" + responses());
        
        return response;
    }

    public static RequestSpecification getRequestSpecification() {
        // 基础信息
        return given().headers(Headers).cookies(Cookies).relaxedHTTPSValidation();

    }

    public static void getURL() {
        // 获取URL
        RestAssured.baseURI = baseURL;

    }

    public static String getDate() {
        // date
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return date.format(new Date());

    }

    public static long getCurrentTime() {
        // 获取当前时间
        long date = System.currentTimeMillis();
        return date;
    }

    public static String responses() {
        // 返回值
        String responses;
        try {
            responses = response.asString();
        } catch (Exception e) {

            responses = null;
        }
        return responses;

    }

    public static String getField(String path) {
        // 获取字段
        String field = response.andReturn().jsonPath().getString(path);
        return field;

    }

    public static Boolean getHas(String name) {
        // 获取字段
        boolean field = response.asString().contains(name);
        return field;

    }

    public static String statuscode() {
        // 获取状态码信息
        String statuscode = response.getStatusCode() + "";
        return statuscode;
    }

    public static String headers() {
        // headers
        io.restassured.http.Headers getHeaders = response.getHeaders();
        return getHeaders.toString();
    }

    public static String time() {
        // 时间
        long timeInSeconds = response.getTime();
        String responsetime = String.valueOf(timeInSeconds);
        return responsetime;

    }

    public static String mapToString(Map<String, Object> map) {
        // map转成string
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            stringBuilder.append(key).append("=").append(map.get(key)).append("&");
        }
        String parameters = stringBuilder.substring(0, stringBuilder.length() - 1);
        return parameters;

    }

    public static void resultCheck(String request, String url, String checkpath, String actual) {
        Reporter.log("——————【正常用例】——————");
        Reporter.log("【请求地址】: " + url);
        Reporter.log("【请求内容】: " + request);
        Reporter.log("【返回结果】: " + responses());
        Reporter.log("【用例结果】: resultCheck=>expected: " + getField(checkpath) + " ,actual: " + actual);
        Assert.assertEquals(getField(checkpath), actual);
    }

    public static void resultCheck(String request, String url, String check) {
        Reporter.log("——————【正常用例】——————");
        Reporter.log("【请求地址】: " + url);
        Reporter.log("【请求内容】: " + request);
        Reporter.log("【返回结果】: " + responses());
        Reporter.log("【用例结果】: resultCheck=>expected: " + check + "是否存在");
        Assert.assertTrue((responses().contains(check)));
    }

}
