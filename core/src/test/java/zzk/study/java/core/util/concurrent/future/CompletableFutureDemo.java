package zzk.study.java.core.util.concurrent.future;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {

    @Test
    public void test_supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);

        Integer rt = future.get();
        Assert.assertEquals(rt.intValue(), 100);
    }

    @Test
    public void test_supplyAsync_with_executor() {

    }

    @Test
    public void test_runAsync_and_supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(
                () -> System.out.println("[1]" + Thread.currentThread().getId() + "--" + Thread.currentThread().getName()));

        CompletableFuture<String> supplyAsync = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("[2]" + Thread.currentThread().getId() + "--" + Thread.currentThread().getName());
                    return "hello";
                });

        System.out.println("[3]" + Thread.currentThread().getId() + "--" + Thread.currentThread().getName());

        //获取返回值
        System.out.println(runAsync.get());
        System.out.println(supplyAsync.get());
    }

}
