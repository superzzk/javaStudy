package zzk.study.java.core.lang.java8.completable_future;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        try {
            System.out.println("-------------test1-------------");
            test1();
            System.out.println("-------------test2-------------");
            test2();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------------test3-------------");
        test3();
    }

    /**
     * 如果在异步线程中出现异常，则导致主线程死等
     **/
    public static void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> { // 模拟执行耗时任务
            System.out.println("task doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // 告诉completableFuture任务已经完成
            completableFuture.complete("ok");
        }).start();

        String result = completableFuture.get();// 获取任务结果，如果没有完成会一直阻塞等待
        System.out.println("计算结果:" + result);
    }

    /**
     * 将异步线程的异常抛出
     **/
    public static void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> { // 模拟执行耗时任务
            System.out.println("task doing...");
            try {
                Thread.sleep(3000);
                int i = 1 / 0;
            } catch (Exception e) { // 告诉completableFuture任务发生异常了
                completableFuture.completeExceptionally(e);
            } // 告诉completableFuture任务已经完成
            completableFuture.complete("ok");
        }).start();
        /*
         * 获取任务结果，如果没有完成会一直阻塞等待
         * 当执行任务发生异常时，调用get()方法的线程将会收到一个 ExecutionException异常，
         * 该异常接收了一个包含失败原因的Exception 参数
         * */
        String result = completableFuture.get();
        System.out.println("计算结果:" + result);
    }


    public static void test3() {
        long start = System.currentTimeMillis(); // 结果集
        List<String> list = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Integer> taskList = Arrays.asList(2, 1, 3, 4, 5, 6, 7, 8, 9, 10);
        // 流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
        CompletableFuture[] cfs = taskList.stream()
                .map(integer -> CompletableFuture
                        .supplyAsync( () -> calc(integer), executorService )
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


}
