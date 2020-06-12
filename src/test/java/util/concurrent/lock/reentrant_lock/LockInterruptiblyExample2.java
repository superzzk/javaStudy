package util.concurrent.lock.reentrant_lock;

import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyExample2 {
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		MyRunnableClass909 myRnbl = new MyRunnableClass909(lock);
		new Thread(myRnbl, "Thread-1").start();
	}
}

class MyRunnableClass909 implements Runnable {
	ReentrantLock lock;
	public MyRunnableClass909(ReentrantLock lock) {
		this.lock = lock;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + " is Waiting to get the lock");

		try {
			int i = 1;
			Thread t = new Thread();
			while (i < 5) {
				System.out.println("     " + t.getThreadGroup());
				if (i == 2)
					System.out.println("     " + t.getThreadGroup());
				if (i == 3)
					System.out.println("     " + t.isInterrupted());
				if (i == 4)
					System.out.println("     " + t.getStackTrace());

				i = i + 1;
			}

			assert !lock.isHeldByCurrentThread();
			lock.lock();

			System.out.println(" after sleep(1500) Is held by Current Thread - " + lock.isHeldByCurrentThread());
		} finally {
			lock.unlock();
		}
	}
}
