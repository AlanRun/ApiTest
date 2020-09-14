package com.alan.tests.cases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

import static io.restassured.RestAssured.given;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

public class RequestSpecBuilderTest {
    
    public RequestSpecification requestSpc;
    public ResponseSpecification responseSpc;

    @BeforeClass
    public void setUp(){
        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.addParam("userId", "2");
        requestBuilder.addHeader("Accept-Encoding", "gzip, deflate");
        
        requestSpc = requestBuilder.build();
        
        ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
        responseBuilder.expectStatusCode(200);
        responseBuilder.expectHeader("Content-Type", "application/json; charset=utf-8");
        responseBuilder.expectHeader("Cache-Control", "public, max-age=14400");
        
        responseSpc = responseBuilder.build();
    }
    
    @Test
    public void test2(){
        String url = "http://jsonplaceholder.typicode.com/posts?userId=2";
        given().
            get(url).
        then().
            spec(responseSpc).
            time(lessThan(2000L));
    }
    
    
    @Test
    public void test1(){
        String url = "http://jsonplaceholder.typicode.com:80/posts";
        given().
            spec(requestSpc).log().all().
        when().
            get(url).
        then().
            statusCode(200).log().all();
    }
    
}
