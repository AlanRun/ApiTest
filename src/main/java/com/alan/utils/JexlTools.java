package com.alan.utils;

import org.apache.commons.jexl3.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Alan Huang
 */
public class JexlTools {

    /**
     * 自定义数学函数 (在这里增加方法即可扩展自定义函数)
     *
     */
    public static class JexlMath {

        public BigDecimal sin(BigDecimal x) {
            return BigDecimal.valueOf(Math.sin(x.doubleValue()));
        }

        public BigDecimal cos(BigDecimal x) {
            return BigDecimal.valueOf(Math.cos(x.doubleValue()));
        }

        public BigDecimal abs(BigDecimal x) {
            return x.compareTo(BigDecimal.ZERO) >= 0 ? x : BigDecimal.ZERO.subtract(x);
        }

        public BigDecimal sum(BigDecimal x, BigDecimal y) {
            return x.add(y);
        }

        public BigDecimal sum(BigDecimal x, BigDecimal y, BigDecimal z) {
            return x.add(y).add(z);
        }

        public BigDecimal mul(Object input, Object b1) {
            BigDecimal result = new BigDecimal(input.toString()).multiply(new BigDecimal(b1.toString()));  // inch convert cm
            result = new BigDecimal(result.toString()).setScale(1, BigDecimal.ROUND_UP);
            return result;
        }

        public int eqa(String input, String [] arr) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals(input)) {
                    return i;
                }
            }
            return -1;
        }

        public long stringDateToLong (Object input, String formatStr) {
            try {
                return new SimpleDateFormat(formatStr).parse(input.toString()).getTime();
            } catch (ParseException e) {
                return 0;
            }
        }
    }

    public static void main (String [] args) {
        JexlMath jt = new JexlMath();
        String input = "Y";
        int result = jt.eqa(input, new String[]{"N", "Y"});
        System.out.println(result);
    }

    /**
     * 计算表达式
     *
     * @param expression 表达式
     * @param vars 变量值
     * @return 计算结果
     */
    public static Object evaluate(String expression, Map<String, ?> vars) {
        JexlContext context = new MapContext();
        for (Entry<String, ?> entry : vars.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Number) {
                context.set(entry.getKey(), BigDecimal.valueOf(((Number) value).doubleValue()));
            } else {
                context.set(entry.getKey(), value);
            }
        }
        JexlScript e = jl3.createScript(expression);
        return e.execute(context);
    }

    /**
     * 计算表达式
     *
     * @param expression 表达式
     * @param var 变量值
     * @return 计算结果
     */
    public static Object evaluate(String expression, Object var) {
        JexlContext context = new MapContext();
        context.set("input", var);
        JexlScript e = jl3.createScript(expression);
        return e.execute(context);
    }

    private static JexlEngine jl3;
    static {
        Map<String, Object> funcs = new HashMap<String, Object>();
        funcs.put(null, new JexlMath());
        jl3 = new JexlBuilder().namespaces(funcs).create();
    }
}