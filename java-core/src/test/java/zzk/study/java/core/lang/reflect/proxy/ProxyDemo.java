package zzk.study.java.core.lang.reflect.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/21 5:32 PM
 */
public class ProxyDemo {

    /**
     * 测试静态代理
     * */
    @Test
    public void staticProxyTest() {
        IUserService userService = new UserServiceImpl();
        UserServiceProxy userServiceProxy = new UserServiceProxy(userService);

        // this is userService
        // reques cost :0
        userServiceProxy.request();
    }

    /**
     * 测试动态代理
     * */
    @Test
    public void dynamicProxyTest() {
        IUserService userService = (IUserService) Proxy.newProxyInstance(
                IUserService.class.getClassLoader(),
                new Class[]{IUserService.class},
                new RequestCostInvocationHandler(new UserServiceImpl()));

        // this is userService
        // reques cost :0
        userService.request();
    }

    public static interface IUserService {
        public void request();
    }

    public static class UserServiceImpl implements IUserService {
        @Override
        public void request() {
            System.out.println("this is userService");
        }
    }

    /**
     * 静态代理
     * */
    public static class UserServiceProxy implements IUserService {

        private IUserService userService;

        public UserServiceProxy(IUserService userService) {
            this.userService = userService;
        }

        @Override
        public void request() {
            long startTime = System.currentTimeMillis();
            userService.request();
            System.out.println("reques cost :" + (System.currentTimeMillis() - startTime));
        }
    }

    /**
     * 动态代理
     */
    public static class RequestCostInvocationHandler implements InvocationHandler {

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
    }
}
