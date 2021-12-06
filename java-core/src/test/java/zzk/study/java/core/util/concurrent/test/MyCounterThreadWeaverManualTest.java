package zzk.study.java.core.util.concurrent.test;

import com.google.testing.threadtester.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * https://www.baeldung.com/java-testing-multithreaded
 *
 */
public class MyCounterThreadWeaverManualTest {

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

	@Test
	public void testCounter() {
		new AnnotatedTestRunner().runTests(this.getClass(), MyCounter.class);
	}

}