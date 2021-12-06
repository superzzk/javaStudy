package zzk.study.java.core.util.timer;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerDemo {
	@Test
	public void givenUsingTimer_whenSchedulingTaskOnce_thenCorrect() {
		TimerTask task = new TimerTask() {
			public void run() {
				System.out.println("Task performed on: " + new Date() + "n" +
						"Thread's name: " + Thread.currentThread().getName());
			}
		};
		Timer timer = new Timer("Timer");

		long delay = 1000L;
		timer.schedule(task, delay);
	}

	@Test
	public void test_schedule_date() throws InterruptedException {
		List<String> oldDatabase = Arrays.asList("Harrison Ford", "Carrie Fisher", "Mark Hamill");
		List<String> newDatabase = new ArrayList<>();

		LocalDateTime twoSecondsLater = LocalDateTime.now().plusSeconds(2);
		Date twoSecondsLaterAsDate = Date.from(twoSecondsLater.atZone(ZoneId.systemDefault()).toInstant());

		new Timer().schedule(new DatabaseMigrationTask(oldDatabase, newDatabase), twoSecondsLaterAsDate);

		while (LocalDateTime.now().isBefore(twoSecondsLater)) {
			Assert.assertEquals("", 0, newDatabase.size());
			Thread.sleep(500);
		}

		Assert.assertEquals("", 3, newDatabase.size());
	}

	@Test
	public void test_fix_delay() throws InterruptedException {
		new Timer().schedule(new NewsletterTask(), 0, 1000);

		for (int i = 0; i < 3; i++) {
			Thread.sleep(1000);
		}
	}

	@Test
	public void test_fix_rate() throws InterruptedException {
		new Timer().scheduleAtFixedRate(new NewsletterTask(), 0, 1000);

		for (int i = 0; i < 3; i++) {
			Thread.sleep(1000);
		}
	}

	@Test
	public void givenUsingTimer_whenSchedulingDailyTask_thenCorrect() {
		TimerTask repeatedTask = new TimerTask() {
			public void run() {
				System.out.println("Task performed on " + new Date());
			}
		};
		Timer timer = new Timer("Timer");

		long delay = 1000L;
		long period = 1000L * 60L * 60L * 24L;
		timer.scheduleAtFixedRate(repeatedTask, delay, period);
	}

	@Test
	public void givenUsingTimer_whenCancelingTimerTask_thenCorrect() throws InterruptedException {
		TimerTask task = new TimerTask() {
			public void run() {
				System.out.println("Task performed on " + new Date());
				cancel();
			}
		};
		Timer timer = new Timer("Timer");

		timer.scheduleAtFixedRate(task, 1000L, 1000L);

		Thread.sleep(1000L * 2);
	}

	@Test
	public void givenUsingTimer_whenCancelingTimer_thenCorrect() throws InterruptedException {
		TimerTask task = new TimerTask() {
			public void run() {
				System.out.println("Task performed on " + new Date());
			}
		};
		Timer timer = new Timer("Timer");

		timer.scheduleAtFixedRate(task, 1000L, 1000L);

		Thread.sleep(1000L * 2);
		timer.cancel();
	}

	@Test
	public void givenUsingTimer_whenStoppingThread_thenTimerTaskIsCancelled()
			throws InterruptedException {
		TimerTask task = new TimerTask() {
			public void run() {
				System.out.println("Task performed on " + new Date());
				// TODO: stop the thread here
			}
		};
		Timer timer = new Timer("Timer");

		timer.scheduleAtFixedRate(task, 1000L, 1000L);

		Thread.sleep(1000L * 2);
	}


	/**
	 *  the main differences between the Timer and the ExecutorService solution:
	 *
	 * Timer can be sensitive to changes in the system clock; ScheduledThreadPoolExecutor is not
	 * Timer has only one execution thread; ScheduledThreadPoolExecutor can be configured with any number of threads
	 * Runtime Exceptions thrown inside the TimerTask kill the thread, so following scheduled tasks won't run further;
	 * with ScheduledThreadExecutor – the current task will be canceled, but the rest will continue to run
	 * */
	@Test
	public void givenUsingExecutorService_whenSchedulingRepeatedTask_thenCorrect() throws InterruptedException {
		TimerTask repeatedTask = new TimerTask() {
			public void run() {
				System.out.println("Task performed on " + new Date());
			}
		};
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		long delay  = 1000L;
		long period = 1000L;
		executor.scheduleAtFixedRate(repeatedTask, delay, period, TimeUnit.MILLISECONDS);
		Thread.sleep(delay + period * 3);
		executor.shutdown();
	}

	class DatabaseMigrationTask extends TimerTask {
		private List<String> oldDatabase;
		private List<String> newDatabase;

		public DatabaseMigrationTask(List<String> oldDatabase, List<String> newDatabase) {
			this.oldDatabase = oldDatabase;
			this.newDatabase = newDatabase;
		}

		@Override
		public void run() {
			newDatabase.addAll(oldDatabase);
		}
	}

	class NewsletterTask extends TimerTask {
		@Override
		public void run() {
			System.out.println("Email sent at: "
					+ LocalDateTime.ofInstant(Instant.ofEpochMilli(scheduledExecutionTime()),
					ZoneId.systemDefault()));
		}
	}

}
