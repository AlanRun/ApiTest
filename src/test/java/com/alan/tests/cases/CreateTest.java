package com.alan.tests.cases;

import org.testng.annotations.Test;

import com.alan.tests.base.TestBase;


public class CreateTest extends TestBase {
    
    @Test
    public void test01_Create(){
        res = req.
                param("name", "anthony@163.com").
                param("job", "tester").
                header("Content-Type","text/html").
                log().all().
                when().post("/users");
        testUtils.checkStatusCode(res, 201);
        testUtils.printResponseBody(res);
    }
}
