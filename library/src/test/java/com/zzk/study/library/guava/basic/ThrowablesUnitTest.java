package com.zzk.study.library.guava.basic;

import com.google.common.base.Throwables;
import org.junit.Test;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertTrue;

public class ThrowablesUnitTest {

    @Test(expected = RuntimeException.class)
    public void whenThrowable_shouldWrapItInRuntimeException() throws Exception {
        try {
            throwThrowable(Throwable::new);
        } catch (Throwable t) {
            Throwables.propagateIfPossible(t, Exception.class);
            throw new RuntimeException(t);
        }
    }

    @Test(expected = Error.class)
    public void whenError_shouldPropagateAsIs() throws Exception {
        try {
            throwThrowable(Error::new);
        } catch (Throwable t) {
            Throwables.propagateIfPossible(t, Exception.class);
            throw new RuntimeException(t);
        }
    }

    @Test(expected = Exception.class)
    public void whenException_shouldPropagateAsIs() throws Exception {
        try {
            throwThrowable(Exception::new);
        } catch (Throwable t) {
            Throwables.propagateIfPossible(t, Exception.class);
            throw new RuntimeException(t);
        }
    }
    @Test
    public void whenGettingLazyStackTrace_ListShouldBeReturned() throws Exception {
        IllegalArgumentException e = new IllegalArgumentException("Some argument is incorrect");

        List<StackTraceElement> stackTraceElements = Throwables.lazyStackTrace(e);

        assertTrue(stackTraceElements.size() > 0);
    }

    private <T extends Throwable> void throwThrowable(Supplier<T> exceptionSupplier) throws Throwable {
        throw exceptionSupplier.get();
    }

}