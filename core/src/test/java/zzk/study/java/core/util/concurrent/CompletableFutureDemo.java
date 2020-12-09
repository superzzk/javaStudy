package zzk.study.java.core.util.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {

	@Test
	public void test_supplyAsync() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> future =  CompletableFuture.supplyAsync(()-> 100);

		Integer rt = future.get();
		Assert.assertEquals(rt.intValue(), 100);
	}

	@Test
	public void test_supplyAsync_with_executor(){

	}


}
