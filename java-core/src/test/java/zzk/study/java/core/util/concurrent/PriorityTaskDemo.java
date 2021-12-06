package zzk.study.java.core.util.concurrent;

import org.junit.Test;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityTaskDemo {
	public enum JobPriority { HIGH, MEDIUM, LOW}

	public class Job implements Runnable {
		private String jobName;
		private JobPriority jobPriority;

		public Job(String jobName, JobPriority jobPriority) {
			this.jobName = jobName;
			this.jobPriority = jobPriority != null ? jobPriority : JobPriority.MEDIUM;
		}

		public JobPriority getJobPriority() {
			return jobPriority;
		}

		@Override
		public void run() {
			try {
				System.out.println("Job:" + jobName + " Priority:" + jobPriority);
				Thread.sleep(1000);
			} catch (InterruptedException ignored) {
			}
		}
	}
	public class PriorityJobScheduler {

		private ExecutorService priorityJobPoolExecutor;
		private ExecutorService priorityJobScheduler =
				Executors.newSingleThreadExecutor();
		private PriorityBlockingQueue<Job> priorityQueue;

		public PriorityJobScheduler(Integer poolSize, Integer queueSize) {
			priorityJobPoolExecutor = Executors.newFixedThreadPool(poolSize);
			priorityQueue = new PriorityBlockingQueue<Job>(queueSize, Comparator.comparing(Job::getJobPriority));

			priorityJobScheduler.execute(()->{
				while (true) {
					try {
						priorityJobPoolExecutor.execute(priorityQueue.take());
					} catch (InterruptedException e) {
						// exception needs special handling
						break;
					}
				}
			});
		}

		public void scheduleJob(Job job) { priorityQueue.add(job); }
		public int getQueuedTaskCount() { return priorityQueue.size(); }

		protected void close(ExecutorService scheduler) {
			scheduler.shutdown();
			try {
				if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
					scheduler.shutdownNow();
				}
			} catch (InterruptedException e) {
				scheduler.shutdownNow();
			}
		}

		public void closeScheduler() {
			close(priorityJobPoolExecutor);
			close(priorityJobScheduler);
		}
	}

	@Test
	public void whenMultiplePriorityJobsQueued_thenHighestPriorityJobIsPicked() {
		int POOL_SIZE = 1;
		int QUEUE_SIZE = 10;

		Job job1 = new Job("Job1", JobPriority.LOW);
		Job job2 = new Job("Job2", JobPriority.MEDIUM);
		Job job3 = new Job("Job3", JobPriority.HIGH);
		Job job4 = new Job("Job4", JobPriority.MEDIUM);
		Job job5 = new Job("Job5", JobPriority.LOW);
		Job job6 = new Job("Job6", JobPriority.HIGH);

		PriorityJobScheduler pjs = new PriorityJobScheduler(POOL_SIZE, QUEUE_SIZE);

		pjs.scheduleJob(job1);
		pjs.scheduleJob(job2);
		pjs.scheduleJob(job3);
		pjs.scheduleJob(job4);
		pjs.scheduleJob(job5);
		pjs.scheduleJob(job6);

		// ensure no tasks is pending before closing the scheduler
		while (pjs.getQueuedTaskCount() != 0);

		// delay to avoid job sleep (added for demo) being interrupted
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}

		pjs.closeScheduler();
	}


}
