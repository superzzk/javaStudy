package lang.thread;

import org.junit.Test;

public class UncaughtExceptionHandlerDemo {
	Thread t = new Thread() {

		@Override
		public void run() {
			int i=1/0;
		}
	};

	@Test
	public void test(){
		t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("uncaught exception: " + t.toString());
			}
		});

		t.start();
	}
}
