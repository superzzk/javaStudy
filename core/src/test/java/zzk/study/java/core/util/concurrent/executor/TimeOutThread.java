package zzk.study.java.core.util.concurrent.executor;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class TimeOutThread {
	@Test
	public void test() throws Exception {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> future = executor.submit(new Task());

		try {
			System.out.println("Started..");
			System.out.println(future.get(3, TimeUnit.SECONDS));
			System.out.println("Finished!");
		} catch (TimeoutException e) {
			future.cancel(true);
			System.out.println("Terminated!");
		}

		executor.shutdownNow();
	}

	@Test
	public void test2() throws Exception {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> future = executor.submit(new Task2());

		try {
			System.out.println("Started..");
			System.out.println(future.get(3, TimeUnit.SECONDS));
			System.out.println("Finished!");
		} catch (TimeoutException e) {
			future.cancel(true);
			System.out.println("Terminated!");
		}

		executor.shutdownNow();
	}


	class Task implements Callable<String> {
		@Override
		public String call() throws Exception {
			System.out.println("task running...");
			Thread.sleep(4000); // Just to demo a long running task of 4 seconds.
			System.out.println("task complete");
			return "Ready!";
		}
	}

	class Task2 implements Callable<String> {
		@Override
		public String call() {
			System.out.println("task running...");
			try {
				Thread.sleep(4000); // Just to demo a long running task of 4 seconds.
			} catch (InterruptedException e) {
				System.out.println("interrupted exception");
				return null;
			}

			System.out.println("task complete");
			return "Ready!";
		}
	}
}

