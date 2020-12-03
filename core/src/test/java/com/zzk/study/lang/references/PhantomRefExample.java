package com.zzk.study.lang.references;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * Just remember that whilst Weak and Soft references are put in the queue after the object is finalized,
 * Phantom references are put in the queue before. If for any reason you don’t poll the queue,
 * the actual objects referenced by Phantom will not be finalized, and you can incur an OutOfMemory error.
 * */
public class PhantomRefExample {

	/**
	 * run with -Xmx3m -Xms3m
	 *
	 * output:
	 * ref#get(): null
	 * -- Checking whether object garbage collection due --
	 * polledRef: null
	 * Is polledRef same: false
	 * -- do some memory intensive work --
	 * phantom is finalizing.
	 * normal is finalizing.
	 * -- Checking whether object garbage collection due --
	 * polledRef: java.lang.ref.PhantomReference@85ede7b
	 * Is polledRef same: true
	 * Ref#get(): null
	 * pre-mortem cleanup actions
	 *
	 * Process finished with exit code 0
	 * */
	public static void main(String[] args) {
		ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();

		MyObject myObject1 = new MyObject("phantom");
		Reference<MyObject> ref = new PhantomReference<>(myObject1, referenceQueue);
		System.out.println("ref#get(): " + ref.get());
		MyObject myObject2 = new MyObject("normal");

		//make objects unreacheable
		myObject1 = null;
		myObject2 = null;

		if(checkObjectGced(ref, referenceQueue)){
			takeAction();
		}

		System.out.println("-- do some memory intensive work --");
		for (int i = 0; i < 10; i++) {
			int[] ints = new int[100000];
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		if(checkObjectGced(ref, referenceQueue)){
			takeAction();
		}
	}

	private static boolean checkObjectGced(Reference<MyObject> ref, ReferenceQueue<MyObject> referenceQueue) {
		boolean gced = false;
		System.out.println("-- Checking whether object garbage collection due --");
		Reference<? extends MyObject> polledRef = referenceQueue.poll();
		System.out.println("polledRef: "+polledRef);
		System.out.println("Is polledRef same: "+ (gced= polledRef==ref ));
		if(polledRef!=null) {
			System.out.println("Ref#get(): " + polledRef.get());
		}
		return gced;
	}

	private static void takeAction() {
		System.out.println("pre-mortem cleanup actions");
	}

}
