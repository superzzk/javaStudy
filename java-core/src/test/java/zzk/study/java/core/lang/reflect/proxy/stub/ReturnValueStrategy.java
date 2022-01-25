package zzk.study.java.core.lang.reflect.proxy.stub;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * used to substitute a
 * return value in place of executing the real, yet-to-be-implemented method
 * */
public interface ReturnValueStrategy {
    /**
     * Note that getReturnValue is expected to produce the return values
     * for calls to Object.equals, Object.toString, and Object.hashCode.
     */
    Object getReturnValue(Proxy p, Method m, Object[] args, History h ) throws WrappedException;
}
