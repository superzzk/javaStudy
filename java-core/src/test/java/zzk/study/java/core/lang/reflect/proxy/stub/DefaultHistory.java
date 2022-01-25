package zzk.study.java.core.lang.reflect.proxy.stub;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DefaultHistory implements History {
    public long recordMethodCall(Proxy p, Method m, Object[] args ) {
        return 0;
    }
    public void recordReturnValue( long callID, Object returnValue ) {}
    public void recordException( long callID, Throwable cause ) {}
}