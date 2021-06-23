package com.alan.tests;

import com.alan.utils.JexlTools;
import org.apache.commons.jexl3.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DemoTest {

    public static void main (String [] args) {
        test1();
    }

    public static void test6() {
        String content = "eqa(input, new String[]{\"N\", \"Y\"})";
        String input = "Y";
        Object result = JexlTools.evaluate(content, input);
        System.out.println(content + " = " + result);
    }

    public static void test5() {
        Long str = 1624272162658l;
        System.out.println(new BigDecimal(str.toString()));
    }
    public static void test3() {
        String content = "eqa(x, y)";
        Map<String, Object> map = new HashMap<>();
        map.put("x", "Y");
        String [] strArr = {"N","Y"};
        map.put("y", strArr);

        Object result = JexlTools.evaluate(content, map);
        System.out.println(content + " = " + result);
    }


    public static void test4() {
        String content = "stringDateToLong(input, 'yyyy-MM-dd HH:mm:ss.SSS')";
        String input = "2021-05-25 14:31:43.957";

        Object result = JexlTools.evaluate(content, input);
        System.out.println(content + " = " + result);
    }
    public static void test2() {
        String content = "if(input.equals('N')){result = 0;} else if(input.equals('Y')){result = 1;}";
        String input = "Y";

        Object result = JexlTools.evaluate(content, input);
        System.out.println(content + " = " + result);
    }

    public static void test1 () {
        String content = "";
        String str = "height#mul(mul(input, 2.54),10)";
        if (str.contains("#")) {
            content = str.split("#")[1];
        }
//            content = "mul(input,2.54)";
        String input = "1.2";
        Object result = JexlTools.evaluate(content, input);
        if(result instanceof Number) {
            System.out.println("num");
        }
        System.out.println(content + " = " + result);
    }

    public static void test0 () {
        String content = "sum(x, -y)";
        Map<String, Number> map = new HashMap<>();
        map.put("x", new BigDecimal("0.9"));
        map.put("y", new BigDecimal("1"));

        Object result = JexlTools.evaluate(content, map);
        System.out.println(content + " = " + result);
    }

    public static Object executeJx (String scriptText, String value) {
//        System.out.println("scriptText=" + scriptText);
        String key = "input";
        // 初始化Jexl构造器
        JexlBuilder jexlBuilder = new JexlBuilder();
        // 创建Jexl表达式引擎
        JexlEngine jexlEngine = jexlBuilder.create();
        // 创建Jexl表达式解析器
        JexlScript jexlScript = jexlEngine.createScript(scriptText);
        // 创建Jexl表达式变量上下文
        JexlContext jexlContext = new MapContext();
        jexlContext.set(key, value);
        // 执行Jexl表达式，得到结果
        jexlScript.execute(jexlContext);
        return jexlContext.get("result");
    }
}
