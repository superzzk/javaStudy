package com.zzk.study.lang.thread;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DaemonThreadDemo {
	class NewThread extends Thread {
		public void run() {
			long startTime = System.currentTimeMillis();
			while (true) {
				for (int i = 0; i < 10; i++) {
					System.out.println(this.getName() + ": New Thread is running..." + i);
					try {
						//Wait for one sec so it doesn't print too fast
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// prevent the Thread to run forever. It will finish it's execution after 2 seconds
				if (System.currentTimeMillis() - startTime > 2000) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		}
	}

	@Test
	@Ignore
	public void whenCallIsDaemon_thenCorrect() {
		NewThread daemonThread = new NewThread();
		NewThread userThread = new NewThread();
		daemonThread.setDaemon(true);
		daemonThread.start();
		userThread.start();

		assertTrue(daemonThread.isDaemon());
		assertFalse(userThread.isDaemon());
	}

	@Test(expected = IllegalThreadStateException.class)
	@Ignore
	public void givenUserThread_whenSetDaemonWhileRunning_thenIllegalThreadStateException() {
		NewThread daemonThread = new NewThread();
		daemonThread.start();
		daemonThread.setDaemon(true);
	}
}
