package zzk.study.java.core.util.concurrent.executor;


import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * from doc:
 * Note: When actions are enclosed in tasks (such as FutureTask) either explicitly or via methods such as submit,
 * these task objects catch and maintain computational exceptions, and so they do not cause abrupt termination,
 * and the internal exceptions are not passed to this method.
 * */
public class ExecutorExceptionHandlerDemo {

	@Test
	public void notCatchException(){
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,1,TimeUnit.MINUTES,new LinkedBlockingQueue<Runnable>());
		executor.submit((Runnable) () -> {
			throw new RuntimeException("Ouch! Got an error.");
		});
		executor.shutdown();
	}

	@Test
	public void failed() throws InterruptedException {
		final ThreadFactory factory = target -> {
			final Thread thread = new Thread(target);
			System.out.println("Creating new worker thread");
			thread.setName("test_thread");
			thread.setUncaughtExceptionHandler((t, e) -> System.out.println("Uncaught Exception" + t.toString()));
			return thread;
		};

		final ExecutorService executor = Executors.newCachedThreadPool(factory);
		executor.submit((Runnable) () -> {
			System.out.println("thread running, thread name:"+Thread.currentThread().getName());
			int a = 1 / 0;
			System.out.println("after exception");
			throw new RuntimeException("Ouch! Got an error.");
		});
		executor.awaitTermination(3, TimeUnit.SECONDS);
	}

	@Test
	public void catchException() {
		ExtendedExecutor threadPool = new ExtendedExecutor();
		threadPool.submit((Runnable) () -> {
			throw new RuntimeException("Ouch! Got an error.");
		});
		threadPool.shutdown();
	}

	public class ExtendedExecutor extends ThreadPoolExecutor {
		public ExtendedExecutor() {
			super(  1, // core threads
					1, // max threads
					1, // timeout
					TimeUnit.MINUTES, // timeout units
					new LinkedBlockingQueue<Runnable>() // work queue
			);
		}

		/* this doesn't work
		protected void afterExecute(Runnable r, Throwable t) {
			super.afterExecute(r, t);
			if(t != null) {
				System.out.println("Got an error: " + t);
			} else {
				System.out.println("Everything's fine--situation normal!");
			}
		}
		*/

		protected void afterExecute(Runnable r, Throwable t) {
			super.afterExecute(r, t);
			if (t == null && r instanceof Future<?>) {
				try {
					Future<?> future = (Future<?>) r;
					if (future.isDone()) {
						future.get();
					}
				} catch (CancellationException ce) {
					t = ce;
				} catch (ExecutionException ee) {
					t = ee.getCause();
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
			}
			if (t != null) {
				System.out.println(t);
			}
		}
	}
}
