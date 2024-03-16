package zzk.study.java.core.util.concurrent.completablefuture;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author zhangzhongkun02
 * @date 2023/6/10 11:34 AM
 */
public class AllOf {
    @Test
    public void allOf_sync(){
        final List<CompletableFuture<Void>> completableFutures = IntStream.range(1, 3).mapToObj(n -> {
            return CompletableFuture.completedFuture("any").thenRun(() -> sleep(1000));
        }).collect(Collectors.toList());

        final long start = System.currentTimeMillis();
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).thenRun(() -> {
            System.out.println("Finish");
        });
        final long end = System.currentTimeMillis();
        assertTrue((end-start) < 100);
    }

    @Test
    public void allOf_async(){
        final List<CompletableFuture<Void>> completableFutures = IntStream.range(1, 3).mapToObj(n -> {
            return CompletableFuture.completedFuture("any").thenRunAsync(() -> sleep(1000));
        }).collect(Collectors.toList());

        final long start = System.currentTimeMillis();
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).thenRun(() -> {
            System.out.println("Finish");
        }).join();
        final long end = System.currentTimeMillis();
        assertTrue((end-start) > 1000);
    }

    @Test
    public void allOf_async_whenComplete(){
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> s.toUpperCase()))
                .collect(Collectors.toList());

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .whenComplete((v, th) -> {
                    futures.forEach(cf -> Assert.assertTrue(StringUtils.isAllUpperCase(cf.getNow(null))));
                    result.append("done");
                });
        allOf.join();
        assertTrue(result.length() > 0, "Result was empty");
    }

    @Test
    public void allOf_whenComplete_exception_handle(){
        final CompletableFuture<Void> future1 = CompletableFuture.completedFuture("any").thenRun(() -> {
            throw new RuntimeException("error");
        });
        final CompletableFuture<String> future2 = CompletableFuture.completedFuture("any");
        CompletableFuture.allOf(future1, future2).whenComplete((rt, th) -> {
            System.out.println("all complete");
            assertNull(rt);
            assertEquals(th.getClass(), CompletionException.class);
            assertEquals(th.getCause().getClass(), RuntimeException.class);
        });
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
