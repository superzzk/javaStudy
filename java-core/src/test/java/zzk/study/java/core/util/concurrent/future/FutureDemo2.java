package zzk.study.java.core.util.concurrent.future;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FutureDemo2 {

    private static final Logger LOG = LoggerFactory.getLogger(FutureDemo2.class);

    @Rule
    public TestName name = new TestName();

    private long start;

    private SquareCalculator squareCalculator;

    @Test
    public void givenExecutorIsSingleThreaded_whenTwoExecutionsAreTriggered_thenRunInSequence() throws InterruptedException, ExecutionException {
        squareCalculator = new SquareCalculator(Executors.newSingleThreadExecutor());

        Future<Integer> result1 = squareCalculator.calculate(4);
        Future<Integer> result2 = squareCalculator.calculate(1000);

        while (!result1.isDone() || !result2.isDone()) {
            LOG.debug(String.format("Task 1 is %s and Task 2 is %s.", result1.isDone() ? "done" : "not done", result2.isDone() ? "done" : "not done"));

            Thread.sleep(300);
        }

        assertEquals(16, result1.get().intValue());
        assertEquals(1000000, result2.get().intValue());
    }

    // 超时异常
    @Test(expected = TimeoutException.class)
    public void whenGetWithTimeoutLowerThanExecutionTime_thenThrowException() throws InterruptedException, ExecutionException, TimeoutException {
        squareCalculator = new SquareCalculator(Executors.newSingleThreadExecutor());

        Future<Integer> result = squareCalculator.calculate(4);

        result.get(500, TimeUnit.MILLISECONDS);
    }

    @Test
    public void givenExecutorIsMultiThreaded_whenTwoExecutionsAreTriggered_thenRunInParallel() throws InterruptedException, ExecutionException {
        squareCalculator = new SquareCalculator(Executors.newFixedThreadPool(2));

        Future<Integer> result1 = squareCalculator.calculate(4);
        Future<Integer> result2 = squareCalculator.calculate(1000);

        while (!result1.isDone() || !result2.isDone()) {
            LOG.debug(String.format("Task 1 is %s and Task 2 is %s.", result1.isDone() ? "done" : "not done", result2.isDone() ? "done" : "not done"));

            Thread.sleep(300);
        }

        assertEquals(16, result1.get().intValue());
        assertEquals(1000000, result2.get().intValue());
    }

    @Test(expected = CancellationException.class)
    public void whenCancelFutureAndCallGet_thenThrowException() throws InterruptedException, ExecutionException, TimeoutException {
        squareCalculator = new SquareCalculator(Executors.newSingleThreadExecutor());

        Future<Integer> result = squareCalculator.calculate(4);

        boolean canceled = result.cancel(true);

        assertTrue("Future was canceled", canceled);
        assertTrue("Future was canceled", result.isCancelled());

        result.get();
    }

    @Before
    public void start() {
        start = System.currentTimeMillis();
    }

    @After
    public void end() {
        LOG.debug(String.format("Test %s took %s ms \n", name.getMethodName(), System.currentTimeMillis() - start));
    }

    class SquareCalculator {

        private final ExecutorService executor;

        SquareCalculator(ExecutorService executor) {
            this.executor = executor;
        }

        Future<Integer> calculate(Integer input) {
            return executor.submit(() -> {
                Thread.sleep(1000);
                return input * input;
            });
        }
    }
}
