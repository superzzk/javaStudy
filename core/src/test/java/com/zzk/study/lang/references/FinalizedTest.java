package com.zzk.study.lang.references;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * https://blog.shiftleft.io/understanding-jvm-soft-references-for-great-good-and-building-a-cache-244a4f7bb85d
 * */
public class FinalizedTest {
	static final int count = 5_000_000;

	/**
	 * to get usable output, run with e.g. `-Xms1g -Xmx1g -XX:SoftRefLRUPolicyMSPerMB=0`
	 */
	public static void main(String[] args) throws Exception {
		ArrayList<SoftReference<Instance>> instances = new ArrayList<>();
		long start = System.currentTimeMillis();

		for (int i = 0; i<count; i++) {
			instances.add(new SoftReference<>(new Instance()));

			if (i % 100_000 == 0) {
				Thread.sleep(100); // in lieu of other application usage
				System.out.println(i + " instances created (in total); free=" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "M");
			}
		}

		System.out.println("time taken: " + (System.currentTimeMillis() - start));
	}

}

class Instance {
	static int finalizedCount = 0;
	String[] occupySomeHeap = new String[50];

	public Instance() {
		InstanceReferenceManager.register(this);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		InstanceReferenceManager.notifyObjectFinalized();
		finalizedCount++;
		if (finalizedCount % 100000 == 0) {
			System.out.println(finalizedCount + " instances finalized (in total)");
		}
	}
}

/**
 * By default, the JVM will free *all* soft references when it runs low on memory.
 * That's a waste of resources, because they'll need to be deserialized back from disk, which is expensive.
 * Instead, we want the GC to only free a small percentage, and therefor we hold onto the rest via strong references.
 */
class InstanceReferenceManager {
	/** maximum number of instances that can remain `soft` in the wild, i.e. we won't hold a strong reference to them */
	public static final int MAX_WILD_SOFT_REFERENCES = 500_000;

	private static int observedInstanceCount = 0;
	private static final ConcurrentLinkedDeque<Instance> strongRefs = new ConcurrentLinkedDeque<>(); //using LinkedList because `Iterator.remove(Object)` is O(1)

	/** called from `Instance` constructor */
	public static void register(Instance instance) {
		observedInstanceCount++;
		if (observedInstanceCount > MAX_WILD_SOFT_REFERENCES) {
			// hold onto a strong reference to this instance, so that it doesn't get freed by the GC when we're low on memory
			strongRefs.add(instance);
		}
	}

	/**
	 * Called from `Instance.finalize()`, i.e. either an instance got normally finalized or it got
	 * freed by the GC because we're low on heap.
	 * I.e. there are now potentially less 'only softly reachable instances' in the wild, so we should
	 * free some strong references (if we have any).
	 */
	public static void notifyObjectFinalized() {
		observedInstanceCount--;
		Iterator<Instance> iterator = strongRefs.iterator();
		if (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
	}
}

/*
truncated output for `-Xms1g -Xmx1g -XX:SoftRefLRUPolicyMSPerMB=0`
2300000 instances created (in total); free=532M
1500000 instances finalized (in total)
2900000 instances created (in total); free=327M
1600000 instances finalized (in total)
3000000 instances created (in total); free=457M
1800000 instances finalized (in total)
*/
