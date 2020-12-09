package zzk.study.java.core.util.concurrent.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceDemo {

	@Test
	public void demo(){
		String initialRef   = "test";
		int    initialStamp = 0;

		AtomicStampedReference<String> atomicStampedReference =
				new AtomicStampedReference<String>(initialRef, initialStamp);

		String reference = atomicStampedReference.getReference();
		System.out.println(reference);
		int stamp = atomicStampedReference.getStamp();
		System.out.println(stamp);

		String newRef = "New object referenced";
		int    newStamp = 1;

		atomicStampedReference.set(newRef, newStamp);
		print(atomicStampedReference);
	}

	@Test
	public void compareAndSet(){
		String initialRef   = "initial value referenced";
		int    initialStamp = 0;

		AtomicStampedReference<String> atomicStringReference =
				new AtomicStampedReference<String>(initialRef, initialStamp);

		String newRef   = "new value referenced";
		int    newStamp = initialStamp + 1;

		boolean exchanged = atomicStringReference
				.compareAndSet(initialRef, newRef, initialStamp, newStamp);
		System.out.println("exchanged: " + exchanged);  //true

		exchanged = atomicStringReference
				.compareAndSet(initialRef, "new string", newStamp, newStamp + 1);
		System.out.println("exchanged: " + exchanged);  //false

		exchanged = atomicStringReference
				.compareAndSet(newRef, "new string", initialStamp, newStamp + 1);
		System.out.println("exchanged: " + exchanged);  //false

		exchanged = atomicStringReference
				.compareAndSet(newRef, "new string", newStamp, newStamp + 1);
		System.out.println("exchanged: " + exchanged);  //true
	}

	public void detect_aba_problem() {
		String initialRef   = "initial value referenced";
		int    initialStamp = 0;

		AtomicStampedReference<String> atomicStringReference =
				new AtomicStampedReference<String>(initialRef, initialStamp);

		int[] stampHolder = new int[1];
		Object ref = atomicStringReference.get(stampHolder);

		if(ref == null){
			//prepare optimistic modification
		}

		//if another thread changes the reference and stamp here, it can be detected

		int[] stampHolder2 = new int[1];
		Object ref2 = atomicStringReference.get(stampHolder);

		if(ref == ref2 && stampHolder[0] == stampHolder2[0]){
			//no modification since optimistic modification was prepared

		} else {
			//retry from scratch.
		}
	}

	private void print(AtomicStampedReference<String> reference) {
		int[] stampHolder = new int[1];
		String ref = reference.get(stampHolder);

		System.out.println("ref = " + ref);
		System.out.println("stamp = " + stampHolder[0]);
	}
}
