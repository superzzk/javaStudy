package zzk.study.java.core.lang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class RequestCostInvocationHandler implements InvocationHandler {

    private Object target;

    public RequestCostInvocationHandler(Object target) {
        this.target = target;
    }

    /** 被代理对象的任何方法被执行时，都会先进入这个方法 */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("request")) {
            long startTime = System.currentTimeMillis();
            // 执行目标对象的方法
            method.invoke(target, args);
            System.out.println("reques cost :" + (System.currentTimeMillis() - startTime));
        }
        return null;
    }

    public static void main(String[] args) {
        // 3个参数解释如下
        // classloader,生成代理类
        // 代理类应该实现的接口
        // 实现InvocationHandler的切面类
        IUserService userService = (IUserService) Proxy.newProxyInstance(IUserService.class.getClassLoader(),
                new Class[]{IUserService.class}, new RequestCostInvocationHandler(new UserServiceImpl()));

        // this is userService
        // reques cost :0
        userService.request();
    }
}