package util.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class ReduceDemo {
	@Test
	public void demo1(){
		Optional accResult = Stream.of(1, 2, 3, 4)
				.reduce((acc, item) -> {
					System.out.println("acc : "  + acc);
					acc += item;
					System.out.println("item: " + item);
					System.out.println("acc+ : "  + acc);
					System.out.println("--------");
					return acc;
				});
	}
	@Test
	public void demo2(){
		int accResult = Stream.of(1, 2, 3, 4)
				.reduce(0, (acc, item) -> {
					System.out.println("acc : "  + acc);
					acc += item;
					System.out.println("item: " + item);
					System.out.println("acc+ : "  + acc);
					System.out.println("--------");
					return acc;
				});
	}
	public void demo3(){
		ArrayList<Integer> accResult_ = Stream.of(1, 2, 3, 4)
				.reduce(new ArrayList<Integer>(),
						new BiFunction<ArrayList<Integer>, Integer, ArrayList<Integer>>() {
							@Override
							public ArrayList<Integer> apply(ArrayList<Integer> acc, Integer item) {
								acc.add(item);
								System.out.println("item: " + item);
								System.out.println("acc+ : " + acc);
								System.out.println("BiFunction");
								return acc;
							}
						}, new BinaryOperator<ArrayList<Integer>>() {
							@Override
							public ArrayList<Integer> apply(ArrayList<Integer> acc, ArrayList<Integer> item) {
								System.out.println("BinaryOperator");
								acc.addAll(item);
								System.out.println("item: " + item);
								System.out.println("acc+ : " + acc);
								System.out.println("--------");
								return acc;
							}
						});
		System.out.println("accResult_: " + accResult_);
	}
}
