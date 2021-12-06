package zzk.study.java.core.util.concurrent.test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;

/**
 * https://www.baeldung.com/java-testing-multithreaded
 *
 */
public class MyCounterMultithreadedTCManualTest extends MultithreadedTestCase {

    private MyCounter counter;

    @Override
    public void initialize() {
        counter = new MyCounter();
    }

    public void thread1() throws InterruptedException {
        counter.increment();
    }

    public void thread2() throws InterruptedException {
        counter.increment();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void finish() {
    	assertEquals(2, counter.getCount());
    }

    @Test
    public void testCounter() throws Throwable {
        TestFramework.runManyTimes(new MyCounterMultithreadedTCManualTest(), 1000);
    }
}
