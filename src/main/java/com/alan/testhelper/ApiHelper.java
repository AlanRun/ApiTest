package com.alan.testhelper;

import java.util.Map;

import com.alan.annotations.RequestMethods;
import com.alan.annotations.RequestParams;

import io.restassured.response.Response;

public interface ApiHelper {

    // http + get + url
    @RequestMethods(protocol = "http", wmethod = "get", description = "body")
    Response HttpGet(
            @RequestParams("url") String url);

    // http + get + url + cookies
    @RequestMethods(protocol = "http", wmethod = "get", description = "cookies")
    Response HttpGetCookies(
            @RequestParams("url") String url,
            @RequestParams("cookies") Map<String, String> map);

    // http + get + key-value + headers, form格式
    @RequestMethods(protocol = "http", wmethod = "get", description = "paramsMap")
    Response HttpGetParamsMapHeaders(
            @RequestParams("url") String url,
            @RequestParams("paramsMap") Map<String, Object> paramsMap,
            @RequestParams("headers") Map<String, Object> headers);

    // https + get + url
    @RequestMethods(protocol = "https", wmethod = "get", description = "body")
    Response HttpsGet(
            @RequestParams("url") String url);

    // https + get + url + cookies
    @RequestMethods(protocol = "https", wmethod = "get", description = "cookies")
    Response HttpsGetCookies(
            @RequestParams("url") String url,
            @RequestParams("cookies") Map<String, String> map);

    // https + get + url + key-value + headers, form格式
    @RequestMethods(protocol = "https", wmethod = "get", description = "paramsMap")
    Response HttpsGetParamsMapHeaders(
            @RequestParams("url") String url,
            @RequestParams("paramsMap") Map<String, Object> paramsMap,
            @RequestParams("headers") Map<String, Object> headers);

    
    // http + post + url + body, json格式
    @RequestMethods(protocol = "http", wmethod = "post", description = "body")
    Response HttpPostBody(
            @RequestParams("url") String url,
            @RequestParams("body") String body);

    // http + post+ url + key-value, form格式
    @RequestMethods(protocol = "http", wmethod = "post", description = "paramsMap")
    Response HttpPostParamsMap(
            @RequestParams("url") String url,
            @RequestParams("paramsMap") Map<String, Object> paramsMap);
    
    // http + post + url + body + headers, json格式
    @RequestMethods(protocol = "http", wmethod = "post", description = "body")
    Response HttpPostBodyheaders(
            @RequestParams("url") String url,
            @RequestParams("body") String body,
            @RequestParams("headers") Map<String, Object> headers);

    // http + post+ url + key-value + headers, form格式
    @RequestMethods(protocol = "https", wmethod = "post", description = "paramsMap")
    Response HttpPostParamsMapHeaders(
            @RequestParams("url") String url,
            @RequestParams("paramsMap") Map<String, Object> paramsMap,
            @RequestParams("headers") Map<String, Object> headers);
    
    // https + post + url + body, json格式
    @RequestMethods(protocol = "https", wmethod = "post", description = "body")
    Response HttpsPostBody(
            @RequestParams("url") String url,
            @RequestParams("body") String body);

    // https + post+ url + body + headers, json格式
    @RequestMethods(protocol = "https", wmethod = "post", description = "body")
    Response HttpsPostBodyHeaders(
            @RequestParams("url") String url,
            @RequestParams("body") String body,
            @RequestParams("headers") Map<String, Object> headers);

    // https + post+ url + key-value, form格式
    @RequestMethods(protocol = "https", wmethod = "post", description = "paramsMap")
    Response HttpsPostParamsMap(
            @RequestParams("url") String url,
            @RequestParams("paramsMap") Map<String, Object> paramsMap);

    // https + post+ url + key-value + headers, form格式
    @RequestMethods(protocol = "https", wmethod = "post", description = "paramsMap")
    Response HttpsPostParamsMapHeaders(
            @RequestParams("url") String url,
            @RequestParams("paramsMap") Map<String, Object> paramsMap,
            @RequestParams("headers") Map<String, Object> headers);
}
