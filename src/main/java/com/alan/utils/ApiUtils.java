package com.alan.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import com.alan.annotations.RequestMethods;
import com.alan.entity.TestGlobal;
import com.alan.exception.AnnoException;

public class ApiUtils {
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> helper) {

        //创建一个代理类
        return (T) Proxy.newProxyInstance(helper.getClassLoader(), new Class[]{helper}, new InvocationHandler() {

            //代理的方法，代理方法接受的参数
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Annotation[] annotations = method.getAnnotations();
                TestGlobal testGlobal = new TestGlobal();
                //目前就一个注解RequestMethods
                if (annotations.length == 0) {
                    throw new AnnoException(String.format("%s方法未配置请求类型注解", method.getName()));
                }
                //参数注解RequestMethods
                String protocol = ((RequestMethods) annotations[0]).protocol();
                testGlobal.setProtocol(protocol);

                String description = ((RequestMethods) annotations[0]).description();
                testGlobal.setDescription(description);

                String wmethod = ((RequestMethods) annotations[0]).wmethod();
                testGlobal.setWmethod(wmethod);

                // 参数注解
                Annotation[][] parameters = method.getParameterAnnotations();
                Integer length = parameters.length;

                if (length == 0) {
                    throw new AnnoException(String.format("%s缺少参数注解", method.getName()));
                } else if (length != 0) {
                    for (Integer i = 0; i < length; i++) {
                        Annotation[] annos = parameters[i];
                        if (annos[0] instanceof RequestMethods) {
                        }
                    }
                    if (length == 1) {
                        testGlobal.setUrl(args[0].toString());
                    } else {
                        if (length == 3) {
                            testGlobal.setHeaders((Map<String, ?>) args[2]);
                        }
                        switch (description) {
                            case "paramsMap":
                                testGlobal.setParams((Map<String, ?>) args[1]);
                                break;
                            case "body":
                                testGlobal.setBody(args[1].toString());
                                break;
                            case "cookies":
                                testGlobal.setCookies((Map<String, ?>) args[1]);
                                break;
                            default:
                                throw new AnnoException(String.format("%s描述参数注解错误", method.getName()));
                        }
                        testGlobal.setUrl(args[0].toString());
                    }
                }
                return HttpUtils.httpRequest(testGlobal);
            }
        });
    }
}
