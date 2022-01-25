package zzk.study.java.core.lang.reflect.proxy.stub;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 1 Construct the object being tested and the stubs that it needs. Set the stubs on the object being tested.
 * 2 In the return value strategies of each of the stubs, script the return values or exceptions that
 *  guide execution along the desired path through the class being tested.
 * 3 Invoke the method being tested on the object.
 * 4 Test the correctness of the return value of the method.
 * 5 Query the history in each of the stubs for the behavior of the object and test its correctness.
 * */
public class StubIH implements InvocationHandler, Stub {
    private ReturnValueStrategy retValStrategy = new DefaultReturnValueStrategy();
    private History history = new DefaultHistory();

    public static Stub createStub(Class[] interfaces, ReturnValueStrategy rvs) {
        return createStub(interfaces, rvs, null);
    }

    public static Stub createStub(Class[] interfaces, ReturnValueStrategy rvs, History h) {
        for (int i = 0; i < interfaces.length; i++) {
            if (interfaces[i] == Stub.class || interfaces[i] == InvocationHandler.class)
                throw new RuntimeException("Cannot stub " + interfaces[i]);
        }

        Class[] newInterfaces = new Class[interfaces.length + 1];
        newInterfaces[0] = Stub.class;
        System.arraycopy(interfaces, 0, newInterfaces, 1, interfaces.length);
        return (Stub) Proxy.newProxyInstance(Stub.class.getClassLoader(), newInterfaces,
                new StubIH(newInterfaces, rvs, h));
    }

    private StubIH(Class[] interfaces, ReturnValueStrategy rvs, History h) {
        if (h != null) history = h;
        if (rvs != null) retValStrategy = rvs;
    }

    public final History getHistory() {
        return history;
    }

    public Object invoke(Object p, Method m, Object[] args) throws Throwable {
        if (m.getDeclaringClass() == Stub.class) {
            // for calls to methods declared in Stub interface
            return m.invoke(this, args);
        }
        long callId = history.recordMethodCall((Proxy) p, m, args);
        try {
            Object result = retValStrategy.getReturnValue((Proxy) p, m, args, history);
            history.recordReturnValue(callId, result);
            return result;
        } catch (WrappedException e) {
            history.recordException(callId, e.getCause());
            throw e.getCause();
        } catch (Exception e) {
            history.recordException(callId, e);
            throw e;
        }
    }
}