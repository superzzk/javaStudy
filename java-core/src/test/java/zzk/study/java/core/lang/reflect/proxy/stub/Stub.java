package zzk.study.java.core.lang.reflect.proxy.stub;

import java.io.Serializable;

public interface Stub extends Serializable {
    /**
     * Returns the history object for the stub.
     */
    History getHistory( );
}