package zzk.study.java.core.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Spliterator;
import java.util.stream.Stream;

public class SpliteratorDemo {
	@Test
	public void demo1() {
		ArrayList<Integer> arrayList = new ArrayList<>();
		arrayList.add(1);
		arrayList.add(2);
		arrayList.add(-3);
		arrayList.add(-4);
		arrayList.add(5);

		Stream<Integer> str = arrayList.stream();

		// getting Spliterator object on arrayList
		Spliterator<Integer> splitr1 = str.spliterator();

		// estimateSize method
		System.out.println("estimate size : " + splitr1.estimateSize());

		// getExactSizeIfKnown method
		System.out.println("exact size : " + splitr1.getExactSizeIfKnown());

		// hasCharacteristics and characteristics method
		System.out.println(splitr1.hasCharacteristics(splitr1.characteristics()));

		System.out.println("Content of arraylist :");
		// forEachRemaining method
		splitr1.forEachRemaining((n) -> System.out.println(n));

		// Obtaining another  Stream to the array list.
		Stream<Integer> str1 = arrayList.stream();
		splitr1 = str1.spliterator();

		// trySplit() method
		Spliterator<Integer> splitr2 = splitr1.trySplit();

		// If splitr1 could be split, use splitr2 first.
		if (splitr2 != null) {
			System.out.println("Output from splitr2: ");
			splitr2.forEachRemaining((n) -> System.out.println(n));
		}

		// Now, use the splitr1
		System.out.println("\nOutput from splitr1: ");
		splitr1.forEachRemaining((n) -> System.out.println(n));
	}

	@Test
	public void demo_tryAdvance_() {
		ArrayList<Integer> arrayList1 = new ArrayList<>();
		arrayList1.add(1);
		arrayList1.add(2);
		arrayList1.add(-3);
		arrayList1.add(-4);
		arrayList1.add(5);

		// Use tryAdvance() to display(action) contents of arraylist.
		System.out.print("Contents of arraylist:\n");

		// getting Spliterator object on arrayList1
		Spliterator<Integer> splitr = arrayList1.spliterator();

		// Use tryAdvance() to display(action) contents of arraylist.
		// Notice how lambda expression is used to implement accept method
		// of Consumer interface
		while (splitr.tryAdvance((n) -> System.out.println(n))) ;

		// Use tryAdvance() for getting absolute values(action) of contents of arraylist.
		// Create new list that contains absolute values.
		ArrayList<Integer> al2 = new ArrayList<>();

		splitr = arrayList1.spliterator();

		// Here our action is to get absolute values
		// Notice how lambda expression is used to implement accept method
		// of Consumer interface
		while (splitr.tryAdvance((n) -> al2.add(Math.abs(n)))) ;

		System.out.print("Absolute values of contents of arraylist:\n");
		// getting Spliterator object on al2
		splitr = al2.spliterator();
		while (splitr.tryAdvance((n) -> System.out.println(n))) ;
	}
}