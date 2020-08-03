package lang.references;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SoftVsWeakVsNormal {

	/**
	 * run with  -Xmx5m -Xms5m
	 * Weak references and normal objects are equally likely to be garbage collected but soft references live longer than them.
	 *
	 * output:
	 * Finalizing: weak 0
	 * Finalizing: normal 5
	 * Finalizing: normal 6
	 * Finalizing: weak 7
	 * Finalizing: normal 7
	 * Finalizing: weak 8
	 * Finalizing: normal 8
	 * Finalizing: normal 9
	 * Finalizing: normal 0
	 * Finalizing: normal 1
	 * Finalizing: normal 2
	 * Finalizing: normal 3
	 * Finalizing: normal 4
	 * Finalizing: weak 5
	 * -- printing references --
	 * Reference: null [WeakReference]
	 * Reference: soft 0 [SoftReference]
	 * Reference: weak 1 [WeakReference]
	 * Reference: soft 1 [SoftReference]
	 * Reference: weak 2 [WeakReference]
	 * Reference: soft 2 [SoftReference]
	 * Reference: weak 3 [WeakReference]
	 * Reference: soft 3 [SoftReference]
	 * Reference: weak 4 [WeakReference]
	 * Reference: soft 4 [SoftReference]
	 * Reference: null [WeakReference]
	 * Reference: soft 5 [SoftReference]
	 * Reference: weak 6 [WeakReference]
	 * Reference: soft 6 [SoftReference]
	 * Reference: null [WeakReference]
	 * Reference: soft 7 [SoftReference]
	 * Reference: null [WeakReference]
	 * Reference: soft 8 [SoftReference]
	 * Reference: weak 9 [WeakReference]
	 * Reference: soft 9 [SoftReference]
	 *
	 * Process finished with exit code 0
	 *
	 * */
	public static void main(String[] args) {
		List<Reference<SoftVsNormal.MyObject>> references = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			//weak
			Reference<SoftVsNormal.MyObject> ref = new WeakReference<>(
					new SoftVsNormal.MyObject("weak " + i));
			references.add(ref);
			//soft
			ref = new SoftReference<>(
					new SoftVsNormal.MyObject("soft " + i));
			references.add(ref);
			//normal
			new SoftVsNormal.MyObject("normal " + i);
		}
		SoftVsNormal.printReferences(references);
	}

}
