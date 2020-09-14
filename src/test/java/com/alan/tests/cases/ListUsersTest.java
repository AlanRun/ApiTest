package com.alan.tests.cases;

import org.testng.annotations.Test;

import com.alan.tests.base.TestBase;

public class ListUsersTest extends TestBase {
    
    @Test
    public void test01_ListUsers(){
        res = req.get("/users?page=2");
        jp = getJsonPath(res);
            
        testUtils.checkStatusCode(res, 200);
        testUtils.printAllResponseText(res);
    }
}
