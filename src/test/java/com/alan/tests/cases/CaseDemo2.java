package com.alan.tests.cases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.alan.data.ParasReplace;
import com.alan.data.SaveParams;
import com.alan.data.StrSubUtil;
import com.alan.data.String2Map;
import com.alan.entity.TestCase;
import com.alan.tests.base.TestBase;
import com.alan.utils.HttpUtils;
import com.alan.verify.CheckPoints;
import com.alibaba.fastjson.JSON;

public class CaseDemo2 extends TestBase{

    @Test
    public void testHttpsGet() {

        response = apiHelper.HttpsGet("https://www.baidu.com/");
    }

    @Test
    public void testPostBody() {

        body.put("testUserName", "tellme");
        response = apiHelper.HttpsPostBody(JSON.toJSONString(body), "https://api.apiopen.top/searchMusic");
        //通过json解析
        logger.info("message>>>>>>>" + HttpUtils.getField("message"));
        //通过string截取
        logger.info("message>>>>>>>" + StrSubUtil.getSubUtilSimple(HttpUtils.responses(), "message\":\"(.*?)\",\"result"));
        Assert.assertEquals(HttpUtils.getField("message"), "成功!");

    }

    @Test
    public void testPostBodyHeader() {

        body.put("testUserName", "tellme");
        Headers.put("Accept", "application/json, text/plain, */*");
        response = apiHelper.HttpsPostBodyHeaders(JSON.toJSONString(body), "https://api.apiopen.top/searchMusic", Headers);
        Assert.assertEquals(HttpUtils.getField("code"), "200");

    }

    @Test
    public void testcheckByJsonPath() {

        body.put("testUserName", "tellme");

        response = apiHelper.HttpsPostBody(JSON.toJSONString(body), "https://api.apiopen.top/searchMusic");
        //通过jsonPath检查
        CheckPoints.checkByJsonPath(HttpUtils.responses(), "$.code=200;$.message=成功!");

        //关联
        SaveParams.saveMapbyJsonPath(HttpUtils.responses(), "user=$.message;password=$.code");
        logger.info(SaveParams.saveMap.toString());

    }

    @Test
    public void testParasReplace() {

        body.put("testUserName", "tellme");
        response = apiHelper.HttpsPostBody(JSON.toJSONString(body), "https://api.apiopen.top/searchMusic");

        //通过jsonPath检查
        CheckPoints.checkByJsonPath(HttpUtils.responses(), "$.code=200;$.message=成功!");

        TestCase testCase = new TestCase();
        testCase.setUrl("https://api.apiopen.top/searchMusic");
        testCase.setParams("user=${user}&password=${password}");
        testCase.setHeader("user=${user};password=${password};mobile=xxxxxx");

        //关联
        testCase = ParasReplace.matcher(testCase,SaveParams.saveMapbyJsonPath(HttpUtils.responses(), "user=$.message;password=$.code"));

        apiHelper.HttpsPostParamsMapHeaders(testCase.getUrl(), String2Map.covertString2Map(testCase.getParams(), "&"), String2Map.covertString2Map(testCase.getHeader()));

        apiHelper.HttpsPostBodyHeaders(testCase.getUrl(), JSON.toJSONString(String2Map.covertString2Map(testCase.getParams(), "&")), String2Map.covertString2Map(testCase.getHeader()));

    }
}
