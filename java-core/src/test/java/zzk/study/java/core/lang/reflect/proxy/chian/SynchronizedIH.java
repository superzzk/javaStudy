package zzk.study.java.core.lang.reflect.proxy.chian;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SynchronizedIH extends InvocationHandlerBase {

    public static Object createProxy( Object obj ) {
        return Proxy.newProxyInstance( obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new SynchronizedIH( obj ) );
    }

    private SynchronizedIH( Object obj ) { super( obj ); }

    public Object invoke(Object proxy, Method method, Object[] args )
            throws Throwable
    {
        Object result = null;
        synchronized ( this.getRealTarget() ) {
            result = method.invoke( nextTarget, args );
        }
        return result;
    }
}