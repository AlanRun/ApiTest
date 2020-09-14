package com.alan.verify;

import org.testng.Assert;

import io.restassured.response.Response;

public class TestUtils {

    /**
     * 检查响应状态码
     * @param res
     * @param statusCode
     */
    public void checkStatusCode(Response res, int statusCode){
        Assert.assertEquals(res.statusCode(), statusCode, "状态检查失败！");
    }
    
    /**
     * 打印所有响应内容
     * @param res
     */
    public void printAllResponseText(Response res) {
        System.out.println(res.then().log().all());
        
    }
    
    /**
     * 打印响应正文
     * @param res
     */
    public void printResponseBody(Response res){
        
        System.out.println(res.then().log().body());
    }
    
}
