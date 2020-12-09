package zzk.study.java.core.designpattern.behavioral.chain_of_responsibility.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 类似mybatis的Plugin
 */
public class Wrapper implements InvocationHandler {

    /** 目标对象 */
    private final Object target;
    /** Interceptor对象 */
    private final Interceptor interceptor;

    public Wrapper(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    /** 生成代理对象 */
    public static Object wrap(Object target, Interceptor interceptor) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                new Class[]{IGetStr.class},
                new Wrapper(target, interceptor));
    }

    /** 被代理对象的方法执行时，这个方法会被执行 */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 只为方法getStrZero生成代理对象
        if (method.getName().equals("getStrZero")) {
            return interceptor.intercept(new Invocation(target, method, args));
        }
        return method.invoke(target, args);
    }
}