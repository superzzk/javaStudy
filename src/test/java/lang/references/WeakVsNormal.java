package lang.references;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WeakVsNormal {

	/**
	 * run with -Xmx4m -Xms4m
	 *
	 * output:
	 * Finalizing: weak 7
	 * Finalizing: normal 9
	 * Finalizing: weak 9
	 * Finalizing: normal 8
	 * Finalizing: weak 8
	 * Finalizing: normal 7
	 * Finalizing: normal 6
	 * Finalizing: weak 6
	 * Finalizing: normal 5
	 * Finalizing: weak 5
	 * Finalizing: normal 4
	 * Finalizing: weak 4
	 * Finalizing: normal 3
	 * Finalizing: weak 3
	 * Finalizing: normal 2
	 * Finalizing: weak 2
	 * Finalizing: normal 1
	 * Finalizing: weak 1
	 * Finalizing: normal 0
	 * Finalizing: weak 0
	 * -- printing references --
	 * Reference: null [WeakReference]
	 * Reference: null [WeakReference]
	 * Reference: null [WeakReference]
	 * Reference: null [WeakReference]
	 * Reference: null [WeakReference]
	 * Reference: null [WeakReference]
	 * Reference: null [WeakReference]
	 * Reference: null [WeakReference]
	 * Reference: null [WeakReference]
	 * Reference: null [WeakReference]
	 *
	 * Process finished with exit code 0
	 * */
	public static void main(String[] args) {
		List<Reference<SoftVsNormal.MyObject>> references = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			SoftVsNormal.MyObject myObject = new SoftVsNormal.MyObject("weak " + i);
			Reference<SoftVsNormal.MyObject> ref = new WeakReference(myObject);
			references.add(ref);
			new SoftVsNormal.MyObject("normal " + i);
		}
		SoftVsNormal.printReferences(references);
	}

}
