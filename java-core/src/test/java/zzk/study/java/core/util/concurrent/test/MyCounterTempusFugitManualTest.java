package zzk.study.java.core.util.concurrent.test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * https://www.baeldung.com/java-testing-multithreaded
 *
 * we are using two of the Rules available to us from tempus-fugit.
 * These rules intercept the tests and help us apply the desired behaviors, like repetition and concurrency.
 * So, effectively, we are repeating the operation under test ten times each from ten different threads.
 */
public class MyCounterTempusFugitManualTest {

	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

	private static MyCounter counter = new MyCounter();

	private static final int COUNT = 20;
	private static final int REPETITION = 100;

	@Test
	@Concurrent(count = COUNT)
	@Repeating(repetition = REPETITION)
	public void runsMultipleTimes() {
		counter.increment();
	}

	@AfterClass
	public static void annotatedTestRunsMultipleTimes() throws InterruptedException {
		assertEquals(counter.getCount(), COUNT * REPETITION);
	}

}
