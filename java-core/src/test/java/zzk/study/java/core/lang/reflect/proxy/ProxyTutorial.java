package zzk.study.java.core.lang.reflect.proxy;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import zzk.study.java.core.designpattern.behavioral.chain_of_responsibility.interceptor.Invocation;

import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 */
public class ProxyTutorial {
    @Test
    public void getProxyTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 显示创建代理类
        final Class<?> p = Proxy.getProxyClass(this.getClass().getClassLoader(), I.class);
        final Constructor<?> constructor = p.getConstructor(InvocationHandler.class);
        final Imp imp = new Imp();
        final I proxy = (I)constructor.newInstance(new ImpIH(imp));
        proxy.handle();
        assertTrue(Proxy.isProxyClass(p));

        //隐式创建代理类
        I proxy2 = (I)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{I.class}, new ImpIH(imp));
        proxy.handle();
        assertTrue(Proxy.isProxyClass(proxy2.getClass()));
    }

    public interface I{
        void handle();
    }

    public static class Imp implements I{
        @Override
        public void handle() {
            System.out.println(this.getClass().getName() + " : " + "handle");
        }
    }

    public static class ImpIH implements InvocationHandler{
        private Object target;

        public ImpIH(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(this.getClass().getName() + "pre handle");
            method.invoke(target, args);
            System.out.println(this.getClass().getName() + "post handle");
            return null;
        }
    }
}
