package zzk.study.java.core.util.concurrent.test;

import com.google.testing.threadtester.*;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyCounterThreadWeaverUnitTest {

    private MyCounter counter;

    @ThreadedBefore
    public void before() {
        counter = new MyCounter();
    }

    @ThreadedMain
    public void mainThread() {
        counter.increment();
    }

    @ThreadedSecondary
    public void secondThread() {
        counter.increment();
    }

    @ThreadedAfter
    public void after() {
        assertEquals(2, counter.getCount());
    }

    @Ignore
    @Test
    public void testCounter() {
        new AnnotatedTestRunner().runTests(this.getClass(), MyCounter.class);
    }

}