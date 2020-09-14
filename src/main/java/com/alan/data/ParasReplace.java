package com.alan.data;

import com.alan.entity.TestCase;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author wuxi
 * @description 测试用例替换${demo}
 * @date 2020年1月3日
 */
public class ParasReplace {

    private static Logger logger = Logger.getLogger(ParasReplace.class);

    private static Pattern replaceParamPattern = Pattern.compile("\\$\\{(.*?)\\}");

    public static TestCase matcher(TestCase testCase, Map<String, Object> saveMap) {

        Matcher Url = replaceParamPattern.matcher(testCase.getUrl());
        while (Url.find()) {
            String value = saveMap.get(Url.group(1)).toString();
            String newUrl = testCase.getUrl().replace(Url.group(), value);
            testCase.setUrl(newUrl);
        }

        Matcher Header = replaceParamPattern.matcher(testCase.getHeader());
        while (Header.find()) {
            //获取${demo}
            //System.out.println(Header.group());
            //获取 demo
            //System.out.println(Header.group(1));
            String value = saveMap.get(Header.group(1)).toString();
            String newHeader = testCase.getHeader().replace(Header.group(), value);

            testCase.setHeader(newHeader);
        }

        Matcher Params = replaceParamPattern.matcher(testCase.getParams());
        while (Params.find()) {
            String value = saveMap.get(Params.group(1)).toString();
            String newParam = testCase.getParams().replace(Params.group(), value);

            testCase.setParams(newParam);
        }
        logger.info("[ParasReplace]="+testCase);
        return testCase;
    }

    public static void main(String[] args) throws Exception {
        TestCase testCase = new TestCase();
        getClass(testCase);
    }

    public static void getClass(TestCase testCase) throws IllegalAccessException {
        Field[] fields = testCase.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            System.out.println("属性名:" + f.getName() + " 属性值:" + f.get(testCase));

        }
    }
}
