package zzk.study.java.core.util.concurrent.test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyCounterTempusFugitUnitTest {

    @Rule
    public ConcurrentRule concurrently = new ConcurrentRule();
    @Rule
    public RepeatingRule rule = new RepeatingRule();

    private static MyCounter counter = new MyCounter();

    @Ignore
    @Test
    @Concurrent(count = 2)
    @Repeating(repetition = 10)
    public void runsMultipleTimes() {
        counter.increment();
    }

    @AfterClass
    public static void annotatedTestRunsMultipleTimes() throws InterruptedException {
        assertEquals(counter.getCount(), 20);
    }

}
