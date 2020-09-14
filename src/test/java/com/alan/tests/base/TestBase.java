package com.alan.tests.base;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.alan.testhelper.ApiHelper;
import com.alan.utils.ApiUtils;
import com.alan.verify.TestUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TestBase {
    public static Logger logger;

    public static String serverHost;
    public static String port;

    // Global Setup Variables
    public Response res = null; // Response
    public JsonPath jp = null; // JsonPath

    // 测试用例中断言代码能用上这里的 testUtils对象
    public static TestUtils testUtils = new TestUtils();
    public RequestSpecification req;
    public static ResourceBundle rb = ResourceBundle.getBundle("config");
    
    public static ApiHelper apiHelper = ApiUtils.create(ApiHelper.class);
    public static Response response;
    public Map<String, Object> Headers = new HashMap<>();
    public Map<String, Object> body = new HashMap<>();

    static {
        logger = Logger.getLogger(TestBase.class.getName());
        PropertyConfigurator.configure("log4j.properties");
        logger.setLevel(Level.DEBUG);
    }

    @BeforeClass
    public void setUp() {
//        setBaseURI();
//        setBasePath("api");
//        req = RestAssured.given().contentType(ContentType.JSON);
    }

    @AfterClass
    public void afterClass() {
//        resetBaseURI();
//        resetBasePath();
    }

    /**
     * 返回JsonPath对象
     * 
     * @param res
     * @return
     */
    public JsonPath getJsonPath(Response res) {
        String json = res.asString();
        System.out.print("returned json: " + json + "\n");
        return new JsonPath(json);
    }

    /**
     * 设置请求接口域名
     */
    public void setBaseURI() {
        if ("80".equals(port)) {
            RestAssured.baseURI = serverHost;
        } else {
            RestAssured.baseURI = serverHost + ":" + port;
        }
        // System.out.println(RestAssured.baseURI);
    }

    /**
     * 
     * @param basePath
     */
    public void setBasePath(String basePath) {
        RestAssured.basePath = basePath;
    }

    public void resetBasePath() {
        RestAssured.basePath = null;
    }

    public void resetBaseURI() {
        RestAssured.baseURI = null;
    }

    /**
     * 设置请求ContentType
     * 
     * @param type
     */
    public void setContenType(ContentType type) {
        given().contentType(type);
    }

}
