package util.concurrent.executor;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 这种方式捕获异常不成功，why???
 *
 * from doc:
 * Note: When actions are enclosed in tasks (such as FutureTask) either explicitly or via methods such as submit,
 * these task objects catch and maintain computational exceptions, and so they do not cause abrupt termination,
 * and the internal exceptions are not passed to this method.
 * */
public class ExecutorExceptionHandlerDemo {

	@Test
	public void test() throws InterruptedException {
		final ThreadFactory factory = new ThreadFactory() {

			@Override
			public Thread newThread(Runnable target) {
				final Thread thread = new Thread(target);
				System.out.println("Creating new worker thread");
				thread.setName("test_thread");
				thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

					@Override
					public void uncaughtException(Thread t, Throwable e) {
						System.out.println("Uncaught Exception" + t.toString());
					}

				});
				return thread;
			}

		};

		final ExecutorService executor = Executors.newCachedThreadPool(factory);

		executor.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("thread running, thread name:"+Thread.currentThread().getName());
				int a = 1 / 0;

				System.out.println("after exception");
				throw new RuntimeException("Ouch! Got an error.");
			}
		});

		executor.awaitTermination(3, TimeUnit.SECONDS);

	}
}
