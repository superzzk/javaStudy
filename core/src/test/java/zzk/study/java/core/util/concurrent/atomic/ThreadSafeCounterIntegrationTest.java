package zzk.study.java.core.util.concurrent.atomic;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadSafeCounterIntegrationTest {

    
    @Test
    public void givenMultiThread_whenSafeCounterWithoutLockIncrement() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        SafeCounterWithoutLock safeCounter = new SafeCounterWithoutLock();

        IntStream.range(0, 1000)
          .forEach(count -> service.submit(safeCounter::increment));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertEquals(1000, safeCounter.getValue());
    }

    public class SafeCounterWithoutLock {
        private final AtomicInteger counter = new AtomicInteger(0);

        int getValue() {
            return counter.get();
        }

        void increment() {
            while(true) {
                int existingValue = getValue();
                int newValue = existingValue + 1;
                if(counter.compareAndSet(existingValue, newValue)) {
                    return;
                }
            }
        }
    }


}
