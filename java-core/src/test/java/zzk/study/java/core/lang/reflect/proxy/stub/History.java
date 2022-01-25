package zzk.study.java.core.lang.reflect.proxy.stub;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public interface History extends java.io.Serializable {
    long recordMethodCall(Proxy p, Method m, Object[] args );
    void recordReturnValue( long callID, Object returnValue );
    void recordException( long callID, Throwable cause );
}