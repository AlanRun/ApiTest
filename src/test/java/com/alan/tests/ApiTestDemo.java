package com.alan.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.hamcrest.Matcher;

import com.alan.tests.base.TestBase;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTestDemo extends TestBase{

    public static void main(String[] args) {

    }
    
    @Test
    public void testHttpsGet() {

        response = apiHelper.HttpsGet("https://www.baidu.com");
    }
    
    /**
     * 在特定条件下才打印日志
     */
    @Test
    public void testLogUnderConditional() {
        given().
            get("http://jsonplaceholder.typicode.com/photos/").
        then().
            log().ifStatusCodeIsEqualTo(200);
    }
    
    /**
     * 只有发生错误 情况下才打印日志
     */
    @Test
    public void testLogOnlyError() {
        given().
            get("http://jsonplaceholder.typicode.com/phot/").
        then().
            log().ifError();
    }

    
    /**
     * 日志打印1
     */
    @Test
    public void testLogOnly() {
        given().
            get("http://jsonplaceholder.typicode.com/photos/").
        then().
            //log().headers();
            //log().body();
            log().cookies();
    }
    
    
    /**
     * test response time
     */
    @Test
    public void testResponseTime3() {
        String url = "http://jsonplaceholder.typicode.com/photos/";
        given().get(url).then().time(lessThan(2000L));
    }
    
    @Test
    public void testResponseTime2() {
        String url = "http://jsonplaceholder.typicode.com/photos/";
        long t = given().get(url).timeIn(TimeUnit.SECONDS);
        System.out.println(t);
    }
    
    /**
     * 响应时间
     *  包含：http请求加载响应处理时间，加上使用rest assured工具产生的时间之和
     */
    @Test
    public void testResponseTime(){
        String url = "http://jsonplaceholder.typicode.com/photos/";
        long t = given().get(url).time();
        
        System.out.println("Runtime: " + t);
    }
    
    
    /**
     * test cookie in response
     */
    @Test
    public void testCookieInResponse(){
        String url = "http://jsonplaceholder.typicode.com/photos/1/";
        given().
            get(url).
        then().
            log().all().assertThat().cookie("__cfduid", "04feffb7c50000936a469ba200000001");
    }
    
    
    /**
     * 响应正文中的属性使用lambda表达式来断言
     */
    @Test
    public void testBodyParameterInResponse2(){
        String url = "http://jsonplaceholder.typicode.com/photos/1";
        given().
            get(url).
        then().
            body("thumbnailUrl", response -> equalTo("https://via.placeholder.com/150/92c952"));
        
        given().get(url).then().body("thumbnailUrl", endsWith("92c952"));
    }
    
    @Test
    public void testBodyParameterInResponse1(){
        String url = "";
        given().
            get(url).
        then().
            body("thumbnailUrl", new ResponseAwareMatcher<Response>() {
                
                @Override
                public Matcher<?> matcher(Response response) throws Exception {
                    return equalTo("https://via.placeholder.com/150/92c952");
                }
            });
    }
    
    
    /**
     * set content type
     */
    @Test
    public void testSetContentType(){
        given().
            contentType(ContentType.JSON).
            contentType("application/json;charset=UTF-8").
        when().
            get("http://xxx/xxx").
        then().
            statusCode(200).
            log().all();
    }
    
    /**
     * set header or headers
     */
    @Test
    public void testSetHeader(){
        given().
            header("key", "value").
            header("key2", "value2").
            headers("k1", "v1", "k2", "v2").
        when().
            get("http://xxxx").
        then().
            statusCode(200).
            log().all();
    }
    
    
    /**
     * set mul cookies
     */
    @Test
    public void testMulCookiesInRequest(){
        given().cookie("key", "value1", "value2");
        
        Cookie cookie = new Cookie.Builder("some_cookie", "some_value").setSecured(true).setComment("").build();
        
        given().cookie(cookie).when().get("").then().assertThat().body(equalTo("XXX"));
        
        Cookie cookie2 = new Cookie.Builder("", "").setSecured(true).setComment("").build();
        Cookie cookie3 = new Cookie.Builder("", "").setSecured(true).setComment("").build();
        
        Cookies cookies = new Cookies(cookie2, cookie3);
        given().cookies(cookies).when().get("").then().body(equalTo(""));
    }
    
    /**
     * set cookies
     */
    @Test
    public void testSetCookiesInRequest(){
        
        String url = "http://xxxxx.com/globalweather.asmx?op=GetCitiesByCountry";
        given().
            cookie("__utmt", "1").
        when().
            get(url).
        then().
            statusCode(200).
            log().all();
    }
    
    
    /**
     * path parameters type1
     */
    @Test
    public void testParametersType1(){
        String url = "http://jsonplaceholder.typicode.com/{section}/{id}";
        given().
            pathParam("section", "posts").
            pathParam("id", "3").
        when().
            get(url).
        then().
            statusCode(200).
            log().all();
    }
    
    
    /**
     * 请求参数设置：多参数
     */
    @Test
    public void testMulParams() {
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        given().
            param("key1", "val1","val2","val3").
            param("B").
            param("C", list).
        when().
            get("https://xxxx/api/users").
        then().
            statusCode(400);
    }
    
    /**
     * post请求参数数据设置：formParam
     */
    @Test
    public void testFormParam(){
        String url = "https://reqres.in/api/users";
        given().
            formParam("name", "Tom1").
            formParam("job", "Tester").
        when().
            get(url).
        then().
            statusCode(200).log().all();
    }
    
    /**
     * get请求参数数据设置：queryParam
     */
    @Test
    public void testQueryParam(){
        String url = "http://jsonplaceholder.typicode.com/posts/";
        given().
            queryParam("userId", "3").
            queryParam("id", "21").
            queryParam("title", "asperiores ea ipsam voluptatibus modi minima quia sint").
        when().
            get(url).
        then().
            statusCode(200).
            log().all();
    }

    /**
     * 测试获取响应cookie详情
     */
    @Test
    public void testGetCookiesDetails() {

        String url = "http://jsonplaceholder.typicode.com/photos";
        Response res = get(url);

        Cookie c = res.getDetailedCookie("__cfduid");
        System.out.println("判断这个cookies是否有过期时间设定: " + c.hasExpiryDate());
        System.out.println("打印cookies的过期时间: " + c.getExpiryDate());
        System.out.println("判断是否值: " + c.hasValue());

    }

    /**
     * 测试获取响应cookies
     */
    @Test
    public void testGetCookies() {
        String url = "http://jsonplaceholder.typicode.com/photos";
        Response res = get(url);
        Map<String, String> cookies = res.getCookies();

        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            System.err.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    /**
     * 测试得到响应头
     */
    @Test
    public void testGetResponseHeader() {
        String url = "http://jsonplaceholder.typicode.com/photos";
        Response res = get(url);
        String headerCFRAY = res.getHeader("CF-RAY");
        System.err.println("CF-RAY: " + headerCFRAY);

        Headers headers = res.getHeaders();
        for (Header h : headers) {
            System.out.println(h.getName() + ":" + h.getValue());
        }
    }

    /**
     * 校验响应是否满足json schema约束
     */
    @Test
    public void testJsonSchema() {
        String url = "http://jsonplaceholder.typicode.com/photos/1";
        given().get(url).then().assertThat().body(matchesJsonSchemaInClasspath("test-json-schema.json"));
    }

    /**
     * 验证响应文件类型是 xml
     */
    @Test
    public void testResponseContentTypeXml() {
        String url = "http://www.thomas-bayer.com/sqlrest/INVOICE/";
        given().get(url).then().statusCode(200).contentType(ContentType.XML).log().all();
    }

    /**
     * 验证响应文件类型是 Json
     */
    @Test
    public void testResponseContentTypeJson() {
        String url = "http://jsonplaceholder.typicode.com/photos/1";
        given().get(url).then().statusCode(200).contentType(ContentType.JSON).log().all();
    }

    /**
     * 验证响应文件类型是html
     */
    @Test
    public void testResponseContentTypeHtml() {
        String url = "https://www.baidu.com";
        given().get(url).then().statusCode(200).contentType(ContentType.HTML).log().all();
    }

    /**
     * 先拿到响应对象，然后再解析
     */
    @Test
    public void testFirstGetResponseThenDoAction() {
        String url = "http://jsonplaceholder.typicode.com/photos/1";
        Response response = get(url).then().extract().response();

        System.out.println("contentType: " + response.contentType());
        System.out.println("statusCode: " + response.statusCode());
        System.out.println("Href" + response.path("url"));
    }

    /**
     * 使用path方法提取内容,一行代码写法
     */
    @Test
    public void testExtractDeatilsUsingPath2() {
        String url = "http://jsonplaceholder.typicode.com/photos/1";
        String href = get(url).path("url");
        System.out.println(href);
        when().get(href).then().statusCode(200).log().all();

        String href1 = get(url).andReturn().jsonPath().getString("url");
        System.out.println(href1);
        when().get(href1).then().statusCode(200).log().all();
    }

    /**
     * 用path方法提取内容
     */
    @Test
    public void testExtractDetailsUsingPath() {
        String url = "http://jsonplaceholder.typicode.com/photos/1";
        String href = when().get(url).then().contentType(ContentType.JSON).body("albumId", equalTo(1)).log().all()
                .extract().path("url");
        System.out.println(href);
        when().get(href).then().statusCode(200).log().all();
    }

    /**
     * 以字节数组方式拿到响应对象
     */
    @Test
    public void testGetResponseAsByByteArray() {
        String url = "https://reqres.in/api/users/2";
        byte[] bytes = get(url).asByteArray();
        System.out.println("The Response: \n\n" + bytes.length);
        // 遍历这个字节数组看看
        for (byte b : bytes) {
            System.out.print(b + " ");
        }
    }

    /**
     * 以InputStream的方式拿到响应对象
     */
    @Test
    public void getResponseAsInputStream() throws IOException {
        String url = "https://reqres.in/api/users/2";
        InputStream responseAsInputStream = get(url).asInputStream();
        System.out.println("The Response: \n\n" + responseAsInputStream.toString().length());
        responseAsInputStream.close();
    }

    /**
     * 以字符串的方式拿到全部响应
     */
    @Test
    public void getResponseAsString() {
        String url = "https://reqres.in/api/users/2";
        String responseAsString = get(url).asString();
        System.out.println("The Response: \n\n" + responseAsString);
    }

    /**
     * 一个post请求举类
     */
    @Test
    public void testAPostMethod() {
        String url = "https://reqres.in/api/users";
        given().
            param("name", "Anthony123").
            param("job", "tester").
            header("Content-Type", "text/html").
        when().
            post(url).
        then().
            statusCode(201).log().all();
    }

    /**
     * XML: find element and value by xpath
     */
    @Test
    public void testValueByXpath2() {
        String url = "http://www.thomas-bayer.com/sqlrest/INVOICE/";
        given().get(url).then().body(hasXPath("/INVOICEList/INVOICE[text()='20']")).log().all();
    }

    /**
     * XML: find value by Xpath
     */
    @Test
    public void testFindValueByXpath() {
        String url = "http://www.thomas-bayer.com/sqlrest/CUSTOMER/10/";
        given().get(url).then().body(hasXPath("/CUSTOMER/FIRSTNAME", containsString("Sue"))).log().all();
    }

    /**
     * Json: 测试多个测试点一行代码去校验
     */
    @Test
    public void testSingleXMLCompleteTextInOneLine() {
        String url = "http://www.thomas-bayer.com/sqlrest/CUSTOMER/10/";
        given().get(url).then().body("CUSTOMER.text()", equalTo("10SueFuller135 Upland Pl.Dallas")).log().all();
    }

    /**
     * XML: 测试响应内容是单个xml数据,写多个断言点
     */
    @Test
    public void testSingleXMLContents() {
        String url = "http://www.thomas-bayer.com/sqlrest/CUSTOMER/10/";
        given().get(url).then().body("CUSTOMER.ID", equalTo("10")).body("CUSTOMER.FIRSTNAME", equalTo("Sue"))
                .body("CUSTOMER.LASTNAME", equalTo("Fuller")).body("CUSTOMER.STREET", equalTo("135 Upland Pl."))
                .body("CUSTOMER.CITY", equalTo("Dallas")).log().all();
    }

    /**
     * 测试响应内容是xml单个数据
     */
    @Test
    public void testSingleXMLContent() {
        String url = "http://www.thomas-bayer.com/sqlrest/CUSTOMER/10/";
        given().get(url).then().body("CUSTOMER.ID", equalTo("10")).log().all();
    }

    /**
     * 测试 一个请求带多个头部参数和多个请求入参
     */
    @Test
    public void testRequestWithHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("accept-encoding", "gzip,deflate");
        headers.put("accept-language", "zh-CN");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "2");
        parameters.put("id", "14");

        String url = "http://jsonplaceholder.typicode.com/posts";
        given().headers(headers).params(parameters).when().log().all().get(url).then().statusCode(200).log().all();
    }

    /**
     * 测试请求带一个头部字段
     */
    @Test
    public void testRequestWithHeader() {
        String url = "http://jsonplaceholder.typicode.com/posts";
        given().header("accept-encoding", "gzip,deflate").when().log().all().get(url).then().statusCode(200).log()
                .all();
    }

    @Test
    public void testRequestWithParams() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "2");
        parameters.put("id", "14");

        String url = "https://jsonplaceholder.typicode.com/posts";
        given().params(parameters).log().all().when().get(url).then().statusCode(200);
    }

    /**
     * 测试 带参数请求
     */
    @Test
    public void testRequestWithParam() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        given().param("userId", "2").log().all().when().get(url).then().statusCode(200).log().all();
    }

    /**
     * 测试 equalTo()方法
     */
    @Test
    public void testEqualToMethod() {
        String url = "http://jsonplaceholder.typicode.com/posts/3";
        given().get(url).then().
        // body("id", equalTo(3),"title");
        // body("id", equalTo(3),"title",equalTo("ea molestias quasi exercitationem repellat qui ipsa sit aut"));
        // body("id", equalTo(3)).and().body("title",equalTo("ea molestias quasi exercitationem repellat qui ipsa sit aut"));
        // body("id", equalTo(3)).and().body("title", hasItem("exercitationem repellat"));
                body("id", equalTo(3)).and().body("title", hasItems("exercitationem", "repellat"));
    }

    /**
     * 验证状态码，并且打印全部响应内容到控制台
     */
    @Test
    public void testPrintContents() {
        String url = "http://jsonplaceholder.typicode.com/posts/3";
        given().get(url).then().statusCode(200).log().all();
    }

    /**
     * 状态码校验java风格实现
     */
    @Test
    public void testStatusCodeJavaStyle() {
        String url = "https://www.baidu.com";

        // 创建一个RestAssured对象
        RestAssured ra = new RestAssured();
        // 创建一个请求对象
        @SuppressWarnings("static-access")
        RequestSpecification rs = ra.given();
        // 发送请求，拿到响应对象
        Response res = rs.get(url);
        // 判断响应状态码是不是200
        assert res.getStatusCode() == 200;
    }

    /**
     * 简单检查响应状态码
     */
    @Test
    public void testStatusCode() {
        String url = "https://www.baidu.com";
        given().get(url).then().statusCode(200);
    }
}
