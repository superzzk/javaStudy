package zzk.study.java.core.lang.references;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoftVsNormal {
	/**
	 * run with -Xmx4m -Xms4m
	 *
	 * output:
	 * Finalizing: normal 8
	 * Finalizing: normal 3
	 * Finalizing: normal 4
	 * Finalizing: normal 5
	 * Finalizing: normal 6
	 * Finalizing: normal 7
	 * Finalizing: normal 9
	 * Finalizing: normal 0
	 * Finalizing: normal 1
	 * Finalizing: normal 2
	 * -- printing references --
	 * Reference: soft 0 [SoftReference]
	 * Reference: soft 1 [SoftReference]
	 * Reference: soft 2 [SoftReference]
	 * Reference: soft 3 [SoftReference]
	 * Reference: soft 4 [SoftReference]
	 * Reference: soft 5 [SoftReference]
	 * Reference: soft 6 [SoftReference]
	 * Reference: soft 7 [SoftReference]
	 * Reference: soft 8 [SoftReference]
	 * Reference: soft 9 [SoftReference]
	 *
	 * Process finished with exit code 0
	 * */
	public static void main(String[] args) throws InterruptedException {
		List<Reference<MyObject>> references = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			//create soft reference
			MyObject myObject = new MyObject("soft " + i);
			Reference<MyObject> ref = new SoftReference<>(myObject);
			references.add(ref);
			//without wrapping in any reference
			new MyObject("normal " + i);
		}
		//let see which ones' get() will return null
		printReferences(references);
	}

	public static void printReferences(List<Reference<MyObject>> references) {
		ExecutorService ex = Executors.newSingleThreadExecutor();
		ex.execute(() -> {
			try {
				//sleep a little in case if finalizers are currently running
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("-- printing references --");
			references.stream()
					.forEach(SoftVsNormal::printReference);
		});
		ex.shutdown();
	}

	public static void printReference(Reference<MyObject> r) {
		System.out.printf("Reference: %s [%s]%n", r.get(),
				r.getClass().getSimpleName());
	}

	public static class MyObject {
		private int[] ints = new int[1000];
		private final String name;

		public MyObject(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

		@Override
		protected void finalize() throws Throwable {
			System.out.printf("Finalizing: %s%n", name);
		}
	}
}
