package zzk.study.java.core.util.concurrent.completablefuture;

import org.apache.commons.lang3.StringUtils;
import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class CompletableFutureDemo {

    @Test
    public void supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);

        Integer rt = future.get();
        Assert.assertEquals(rt.intValue(), 100);
    }

    @Test
    public void supplyAsync_with_executor() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100, Executors.newCachedThreadPool());

        Integer rt = future.get();
        Assert.assertEquals(rt.intValue(), 100);
    }

    @Test
    public void completedFuture_get_getNow() throws InterruptedException, ExecutionException {
        Future<String> completableFuture = CompletableFuture.completedFuture("Hello");

        String result = completableFuture.get();
        assertEquals("Hello", result);

        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        assertTrue(cf.isDone());
        assertEquals("message", cf.getNow(null));
    }

    @Test
    public void runAsync_demo1() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            assertTrue(Thread.currentThread().isDaemon());
            System.out.println("thread id:" + Thread.currentThread().getId() + "\nthread name:" + Thread.currentThread().getName());
            assertTrue(Thread.currentThread().getName().startsWith("ForkJoinPool"));
            sleep(30);
        });
        assertFalse(cf.isDone());
        sleep(100);
        assertTrue(cf.isDone());
    }

    @Test
    public void runAsync_demo2() throws ExecutionException, InterruptedException {
        Runnable task = () -> {
            assertTrue(Thread.currentThread().getName().startsWith("ForkJoinPool"));
            sleep(1000);
        };
        long begin = System.currentTimeMillis();
        CompletableFuture<Void> future = CompletableFuture.runAsync(task);

        long submitTime = System.currentTimeMillis() - begin;
        assertTrue(submitTime < 1000);

        future.get();
        long processTime = System.currentTimeMillis() - begin;
        assertTrue(processTime > 1000);
    }

    @Test
    public void runAsync_with_executor() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> runAsync2 = CompletableFuture.runAsync(
                () -> System.out.println("thread id:" + Thread.currentThread().getId() + "\nthread name:" + Thread.currentThread().getName()),
                Executors.newSingleThreadExecutor());
        runAsync2.get();
    }

    @Test
    public void thenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future = completableFuture.thenApply(s -> s + " World");

        assertEquals("Hello World", future.get());
    }

    @Test
    public void thenApply_async() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future = completableFuture.thenApplyAsync(s -> s + " World");

        assertEquals("Hello World", future.get());
    }

    @Test
    public void thenApply_async_with_executor() {
        ExecutorService executor = Executors.newFixedThreadPool(3, new ThreadFactory() {
            int count = 1;

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "custom-executor-" + count++);
            }
        });

        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().getName().startsWith("custom-executor-"));
            assertFalse(Thread.currentThread().isDaemon());
            sleep(200);
            return s.toUpperCase();
        }, executor);

        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }


    @Test
    public void all_future_done() throws ExecutionException, InterruptedException {
        //保证所有的future都执行完成
        List<CompletableFuture<?>> futures = new ArrayList<>();

        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(
                () -> System.out.println("[1]" + Thread.currentThread().getId() + "--" + Thread.currentThread().getName()));

        CompletableFuture<String> supplyAsync = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("[2]" + Thread.currentThread().getId() + "--" + Thread.currentThread().getName());
                    return "hello";
                });
        futures.add(supplyAsync);

        System.out.println("[3]" + Thread.currentThread().getId() + "--" + Thread.currentThread().getName());

        // with executor
        CompletableFuture<Void> runAsync2 = CompletableFuture.runAsync(
                () -> System.out.println("[4]" + Thread.currentThread().getId() + "--" + Thread.currentThread().getName()),
                Executors.newSingleThreadExecutor());
        futures.add(runAsync2);

        for (int i = 5; i < 20; i++) {
            final int num = i;
            CompletableFuture<Void> runAsync3 = CompletableFuture.runAsync(
                    () -> System.out.println("[" + num + "]" + Thread.currentThread().getId() + "--" + Thread.currentThread().getName()));
            futures.add(runAsync3);
        }

        //获取返回值
        assertEquals("hello", supplyAsync.get());
        futures.forEach(completableFuture -> {
            try {
                completableFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }


    @Test
    public void thenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<Void> future = completableFuture.thenAccept(s -> System.out.println("Computation returned: " + s));

        future.get();
    }

    @Test
    public void thenRun() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<Void> future = completableFuture.thenRun(() -> System.out.println("Computation finished."));

        future.get();
    }


    @Test
    public void thenCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));

        assertEquals("Hello World", completableFuture.get());
    }

    @Test
    public void thenCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> s1 + s2);

        assertEquals("Hello World", completableFuture.get());
    }

    @Test
    public void anyOf(){
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> s.toUpperCase()))
                .collect(Collectors.toList());
        CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((res, th) -> {
            if(th == null) {
                assertTrue(StringUtils.isAllUpperCase((String) res));
                result.append(res);
            }
        });
        assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void whenUsingThenAcceptBoth_thenWaitForExecutionOfBothFutures() throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> System.out.println(s1 + s2));
    }

    // allOf
    @Test
    public void whenFutureCombinedWithAllOfCompletes_thenAllFuturesAreDone() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Beautiful");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "World");

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);

        // ...

        combinedFuture.get();

        assertTrue(future1.isDone());
        assertTrue(future2.isDone());
        assertTrue(future3.isDone());

        String combined = Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));

        assertEquals("Hello Beautiful World", combined);
    }

    // handle
    @Test
    public void whenFutureThrows_thenHandleMethodReceivesException() throws ExecutionException, InterruptedException {
        String name = null;

        // ...

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                    if (name == null) {
                        throw new RuntimeException("Computation error!");
                    }
                    return "Hello, " + name;
                })
                .handle((s, t) -> s != null ? s : "Hello, Stranger!");

        assertEquals("Hello, Stranger!", completableFuture.get());
    }

    // completeExceptionally
    @Test(expected = ExecutionException.class)
    public void whenCompletingFutureExceptionally_thenGetMethodThrows() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        // ...

        completableFuture.completeExceptionally(new RuntimeException("Calculation failed!"));

        // ...

        completableFuture.get();
    }


    @Test
    public void whenPassingTransformation_thenFunctionExecutionWithThenApply() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> finalResult = compute().thenApply(s -> s + 1);
        assertTrue(finalResult.get() == 11);
    }

    @Test
    public void whenPassingPreviousStage_thenFunctionExecutionWithThenCompose() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> finalResult = compute().thenCompose(this::computeAnother);
        assertTrue(finalResult.get() == 20);
    }

    public CompletableFuture<Integer> compute() {
        return CompletableFuture.supplyAsync(() -> 10);
    }

    public CompletableFuture<Integer> computeAnother(Integer i) {
        return CompletableFuture.supplyAsync(() -> 10 + i);
    }


    @Test
    public void cancel() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message")
                .thenApplyAsync((s)->{
                    sleep(1000);
                    return s.toUpperCase();
                });
        CompletableFuture<String> cf2 = cf.exceptionally(throwable -> "canceled message");
        assertTrue("Was not canceled", cf.cancel(true));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        assertEquals("canceled message", cf2.join());
    }

    @Test
    public void apply_ether() {
        String original = "Message";
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> s.toUpperCase());
        CompletableFuture<String> cf2 = cf1.applyToEither(
                CompletableFuture.completedFuture(original).thenApplyAsync(s -> s.toLowerCase()),
                s -> s + " from applyToEither");
        assertTrue(cf2.join().endsWith(" from applyToEither"));
    }

    @Test
    public void accept_ether() {
        String original = "Message";
        StringBuffer result = new StringBuffer();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> s.toUpperCase())
                .acceptEither(CompletableFuture.completedFuture(original).thenApplyAsync(s -> s.toLowerCase()),
                        s -> result.append(s).append("acceptEither"));
        cf.join();
        assertTrue("Result was empty", result.toString().endsWith("acceptEither"));
    }

    @Test
    public void run_after_both(){
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).runAfterBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                () -> result.append("done"));
        assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void then_accept_both() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).thenAcceptBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                (s1, s2) -> result.append(s1 + s2));
        assertEquals("MESSAGEmessage", result.toString());
    }

    // test cancel
    @Test(expected = CancellationException.class)
    public void whenCancelingTheFuture_thenThrowsCancellationException() throws ExecutionException, InterruptedException {
        Future<String> future = calculateAsyncWithCancellation();
        future.get();
    }

    private Future<String> calculateAsyncWithCancellation() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool()
                .submit(() -> {
                    Thread.sleep(500);
                    completableFuture.cancel(false);
                    return null;
                });

        return completableFuture;
    }

    /**
     * 如果在异步线程中出现异常，则导致主线程死等
     **/
    @Test
    public void complete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> { // 模拟执行耗时任务
            System.out.println("task doing...");
            try {
                //线程未捕获异常会导致主线程死等
//                int i = 1/0;
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 告诉completableFuture任务已经完成
            completableFuture.complete("ok");
        }).start();

        String result = completableFuture.get();// 获取任务结果，如果没有完成会一直阻塞等待
        System.out.println("计算结果:" + result);
    }


    /**
     * 将异步线程的异常抛出
     **/
    @Test
    public void completeExceptionally_1() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> { // 模拟执行耗时任务
            System.out.println("task doing...");
            sleep(2000);
            try {
                int i = 1 / 0;
            } catch (Exception e) { // 告诉completableFuture任务发生异常了
                completableFuture.completeExceptionally(e);
            } // 告诉completableFuture任务已经完成
            completableFuture.complete("ok");
        }).start();

        String result = null;
        try {
            result = completableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("执行异常：" + e.getMessage());
        }
        System.out.println("计算结果:" + result);
    }

    @Test
    public void completeExceptionally_2() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message")
                .thenApplyAsync((s)->{
                    sleep(1000);
                    return s.toUpperCase();
                });
        // handles any exception by returning another message "message upon cancel"
        CompletableFuture<String> exceptionHandler = cf.handle((s, e) -> {
            assertNull(s);
            return (e != null) ? "message upon cancel" : "";
        });
        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());

        try {
            cf.join();
            fail("Should have thrown an exception");
        } catch (CompletionException ex) { // just for testing
            assertEquals("completed exceptionally", ex.getCause().getMessage());
        }
        assertEquals("message upon cancel", exceptionHandler.join());
    }


    @Test
    public void thenApply_and_whenComplete() {
        long start = System.currentTimeMillis();

        // 结果集
        List<String> list = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Integer> taskList = Arrays.asList(2, 1, 3, 4, 5, 6, 7, 8, 9, 10);
        // 流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
        CompletableFuture[] cfs = taskList.stream()
                .map(integer -> CompletableFuture
                        .supplyAsync(() -> calc(integer), executorService)
                        .thenApply(h -> Integer.toString(h))
                        .whenComplete((s, e) -> {
                            System.out.println("任务" + s + "完成!result=" + s + "，异常 e=" + e + "," + new Date());
                            list.add(s);
                        })
                )
                .toArray(CompletableFuture[]::new);

        // 封装后无返回值，必须自己whenComplete()获取
        CompletableFuture.allOf(cfs).join();
        System.out.println("list=" + list + ",耗时=" + (System.currentTimeMillis() - start));
        executorService.shutdown();
    }

    public static int calc(Integer i) {
        try {
            if (i == 1) {
                Thread.sleep(3000);//任务1耗时3秒
            } else if (i == 5) {
                Thread.sleep(5000);//任务5耗时5秒
            } else {
                Thread.sleep(1000);//其它任务耗时1秒
            }
            System.out.println("task线程：" + Thread.currentThread().getName() + "任务i=" + i + ",完成！+" + new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
