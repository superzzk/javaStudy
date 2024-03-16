package com.zzk.study.library.resilence4j;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

import static com.zzk.study.library.resilence4j.RetryDemo.RetryProperties.*;
import static io.github.resilience4j.core.IntervalFunction.ofExponentialBackoff;
import static io.github.resilience4j.core.IntervalFunction.ofExponentialRandomBackoff;
import static java.util.Collections.nCopies;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author zhangzhongkun02
 * @date 2023/12/25 17:34
 */
public class RetryDemo {
    static Logger log = LoggerFactory.getLogger(RetryDemo.class);

    @Before
    public void setUp() {
        service1 = mock(Resilience4jUnitTest.RemoteService.class);
        service = mock(PingPongService.class);
    }

    @Test
    public void whenRetryIsUsed_thenItWorksAsExpected() {
        RetryConfig config = RetryConfig.custom().maxAttempts(2).build();
        RetryRegistry registry = RetryRegistry.of(config);
        Retry retry = registry.retry("my");
        Function<Integer, Void> decorated = Retry.decorateFunction(retry, (Integer s) -> {
            service1.process(s);
            return null;
        });

        when(service1.process(anyInt())).thenThrow(new RuntimeException());
        try {
            decorated.apply(1);
            fail("Expected an exception to be thrown if all retries failed");
        } catch (Exception e) {
            verify(service1, times(2)).process(any(Integer.class));
        }
    }

    @Test
    public void whenRetryExponentialBackoff_thenRetriedConfiguredNoOfTimes() {
        IntervalFunction intervalFn = ofExponentialBackoff(INITIAL_INTERVAL, MULTIPLIER);
        Function<String, String> pingPongFn = getRetryablePingPongFn(intervalFn);

        when(service.call(anyString())).thenThrow(PingPongServiceException.class);
        try {
            pingPongFn.apply("Hello");
        } catch (PingPongServiceException e) {
            verify(service, times(MAX_RETRIES)).call(anyString());
        }
    }

    @Test
    public void whenRetryExponentialBackoffWithoutJitter_thenThunderingHerdProblemOccurs() throws InterruptedException {
        IntervalFunction intervalFn = ofExponentialBackoff(INITIAL_INTERVAL, MULTIPLIER);
        test(intervalFn);
    }

    @Test
    public void whenRetryExponentialBackoffWithJitter_thenRetriesAreSpread() throws InterruptedException {
        IntervalFunction intervalFn = ofExponentialRandomBackoff(INITIAL_INTERVAL, MULTIPLIER, RANDOMIZATION_FACTOR);
        test(intervalFn);
    }

    private void test(IntervalFunction intervalFn) throws InterruptedException {
        Function<String, String> pingPongFn = getRetryablePingPongFn(intervalFn);
        ExecutorService executors = newFixedThreadPool(NUM_CONCURRENT_CLIENTS);
        List<Callable<String>> tasks = nCopies(NUM_CONCURRENT_CLIENTS, () -> pingPongFn.apply("Hello"));

        when(service.call(anyString())).thenThrow(PingPongServiceException.class);

        executors.invokeAll(tasks);
    }

    private Function<String, String> getRetryablePingPongFn(IntervalFunction intervalFn) {
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(MAX_RETRIES)
                .intervalFunction(intervalFn)
                .retryExceptions(PingPongServiceException.class)
                .build();
        Retry retry = Retry.of("pingpong", retryConfig);
        return Retry.decorateFunction(retry, ping -> {
            log.info("Invoked at {}", LocalDateTime.now());
            return service.call(ping);
        });
    }

    static class RetryProperties {
        static final Long INITIAL_INTERVAL = 1000L;
        static final Double MULTIPLIER = 2.0D;
        static final Double RANDOMIZATION_FACTOR = 0.6D;
        static final Integer MAX_RETRIES = 4;
    }

    interface PingPongService {
        String call(String ping) throws PingPongServiceException;
    }

    class PingPongServiceException extends RuntimeException {
        public PingPongServiceException(String reason) {
            super(reason);
        }
    }

    private PingPongService service;
    private Resilience4jUnitTest.RemoteService service1;

    private static final int NUM_CONCURRENT_CLIENTS = 8;



    interface RemoteService {
        int process(int i);
    }


}
