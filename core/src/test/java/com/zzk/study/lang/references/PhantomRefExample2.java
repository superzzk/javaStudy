package com.zzk.study.lang.references;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * In following example, we will see at what time ReferenceQueue.poll() returns the target phantom reference,
 * relative to the time when Object#finalize() method been called.
 * */
public class PhantomRefExample2 {
	private static boolean finishFlag;

	public static void main(String[] args) {
		ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();

		MyObject myObject1 = new MyObject("phantom");
		Reference<MyObject> ref = new PhantomReference<>(myObject1, referenceQueue);
		startMonitoring(referenceQueue, ref);
		System.out.println("ref#get(): " + ref.get());
		MyObject myObject2 = new MyObject("normal ");

		//make objects unreacheable
		myObject1 = null;
		myObject2 = null;

		System.out.println("-- do some memory intensive work --");
		for (int i = 0; i < 10; i++) {
			int[] ints = new int[100000];
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("-- heavy work finished --");
		finishFlag = true;
	}

	private static void startMonitoring(ReferenceQueue<MyObject> referenceQueue, Reference<MyObject> ref) {
		ExecutorService ex = Executors.newSingleThreadExecutor();
		ex.execute(() -> {
			while (referenceQueue.poll()!=ref) {
				//don't hang forever
				if(finishFlag){
					break;
				}
			}
			System.out.println("-- ref gc'ed --");

		});
		ex.shutdown();
	}
}
