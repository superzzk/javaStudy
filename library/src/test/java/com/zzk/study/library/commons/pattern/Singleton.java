package com.zzk.study.library.commons.pattern;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2023/6/18 8:24 PM
 */
public class Singleton extends LazyInitializer<String> {
    @Override
    protected String initialize() throws ConcurrentException {
        return "singleton";
    }

    @Test
    public void name() throws ConcurrentException {
        Singleton s = new Singleton();
        final String s1 = s.get();
        Assertions.assertEquals("singleton", s1);
    }
}
