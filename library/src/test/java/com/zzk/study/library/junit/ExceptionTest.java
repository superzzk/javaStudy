package com.zzk.study.library.junit;

import org.junit.Test;

/**
 * @author zhangzhongkun
 * @since  2019-07-05 10:09
 **/
public class ExceptionTest {

    @Test(expected = StackOverflowError.class)
    public void testRecursiveCall(){
        testRecursiveCall();
    }
}
