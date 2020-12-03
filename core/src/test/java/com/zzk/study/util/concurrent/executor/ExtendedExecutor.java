package com.zzk.study.util.concurrent.executor;

import org.junit.Test;

import java.util.concurrent.*;

public final class ExtendedExecutor extends ThreadPoolExecutor {

	@Test
	public void notCatchException(){
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,1,TimeUnit.MINUTES,new LinkedBlockingQueue<Runnable>());
		executor.submit(
				new Runnable() {
					public void run() {
						throw new RuntimeException("Ouch! Got an error.");
					}
				}
		);
		executor.shutdown();
	}

	@Test
	public void catchException() {
		ExtendedExecutor threadPool = new ExtendedExecutor();
		threadPool.submit(
				new Runnable() {
					public void run() {
						throw new RuntimeException("Ouch! Got an error.");
					}
				}
		);
		threadPool.shutdown();
	}

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