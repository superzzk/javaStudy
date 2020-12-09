package zzk.study.java.core.util.concurrent.forkjoin;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class Demo1 {
	private int[] arr;
	private CustomRecursiveTask customRecursiveTask;

	@Before
	public void init() {
		Random random = new Random();
		arr = new int[50];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = random.nextInt(35);
		}
		customRecursiveTask = new CustomRecursiveTask(arr);
	}


	@Test
	public void callCommonPool_whenExistsAndExpectedType_thenCorrect() {
		ForkJoinPool commonPool = ForkJoinPool.commonPool();
		ForkJoinPool commonPoolTwo = ForkJoinPool.commonPool();

		assertNotNull(commonPool);
		assertEquals(commonPool, commonPoolTwo);
	}

	@Test
	public void executeRecursiveAction_whenExecuted_thenCorrect() {
		CustomRecursiveAction myRecursiveAction = new CustomRecursiveAction("ddddffffgggghhhh");
		ForkJoinPool.commonPool().invoke(myRecursiveAction);

		assertTrue(myRecursiveAction.isDone());
	}

	@Test
	public void executeRecursiveTask_whenExecuted_thenCorrect() {
		ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

		forkJoinPool.execute(customRecursiveTask);
		int result = customRecursiveTask.join();
		assertTrue(customRecursiveTask.isDone());

		forkJoinPool.submit(customRecursiveTask);
		int resultTwo = customRecursiveTask.join();
		assertTrue(customRecursiveTask.isDone());
	}

	@Test
	public void executeRecursiveTaskWithFJ_whenExecuted_thenCorrect() {
		CustomRecursiveTask customRecursiveTaskFirst = new CustomRecursiveTask(arr);
		CustomRecursiveTask customRecursiveTaskSecond = new CustomRecursiveTask(arr);
		CustomRecursiveTask customRecursiveTaskLast = new CustomRecursiveTask(arr);

		customRecursiveTaskFirst.fork();
		customRecursiveTaskSecond.fork();
		customRecursiveTaskLast.fork();
		int result = 0;
		result += customRecursiveTaskLast.join();
		result += customRecursiveTaskSecond.join();
		result += customRecursiveTaskFirst.join();

		assertTrue(customRecursiveTaskFirst.isDone());
		assertTrue(customRecursiveTaskSecond.isDone());
		assertTrue(customRecursiveTaskLast.isDone());
		assertTrue(result != 0);
	}


}

class CustomRecursiveTask extends RecursiveTask<Integer> {
	private int[] arr;
	private static final int THRESHOLD = 20;

	public CustomRecursiveTask(int[] arr) {
		this.arr = arr;
	}

	@Override
	protected Integer compute() {
		if (arr.length > THRESHOLD) {
			return ForkJoinTask.invokeAll(createSubtasks()).stream().mapToInt(ForkJoinTask::join).sum();
		} else {
			return processing(arr);
		}
	}

	private Collection<CustomRecursiveTask> createSubtasks() {
		List<CustomRecursiveTask> dividedTasks = new ArrayList<>();
		dividedTasks.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, 0, arr.length / 2)));
		dividedTasks.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
		return dividedTasks;
	}

	private Integer processing(int[] arr) {
		return Arrays.stream(arr).filter(a -> a > 10 && a < 27).map(a -> a * 10).sum();
	}
}

class CustomRecursiveAction extends RecursiveAction {

	private String workLoad = "";
	private static final int THRESHOLD = 4;

	private static Logger logger = Logger.getAnonymousLogger();

	public CustomRecursiveAction(String workLoad) {
		this.workLoad = workLoad;
	}

	@Override
	protected void compute() {

		if (workLoad.length() > THRESHOLD) {
			ForkJoinTask.invokeAll(createSubtasks());
		} else {
			processing(workLoad);
		}
	}

	private Collection<CustomRecursiveAction> createSubtasks() {

		List<CustomRecursiveAction> subtasks = new ArrayList<>();

		String partOne = workLoad.substring(0, workLoad.length() / 2);
		String partTwo = workLoad.substring(workLoad.length() / 2, workLoad.length());

		subtasks.add(new CustomRecursiveAction(partOne));
		subtasks.add(new CustomRecursiveAction(partTwo));

		return subtasks;
	}

	private void processing(String work) {
		String result = work.toUpperCase();
		logger.info("This result - (" + result + ") - was processed by " + Thread.currentThread()
				.getName());
	}
}