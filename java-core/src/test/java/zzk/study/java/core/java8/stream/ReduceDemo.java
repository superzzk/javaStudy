package zzk.study.java.core.java8.stream;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ReduceDemo {

	public static String getLastElementUsingReduce(List<String> valueList) {
		Stream<String> stream = valueList.stream();
		return stream.reduce((first, second) -> second).orElse(null);
	}

	public static Integer getInfiniteStreamLastElementUsingReduce() {
		Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
		return stream.limit(20).reduce((first, second) -> second).orElse(null);
	}

	@Test
	public void givenStreamValues_whenReducedWithPrefixingOperation() {
		String result = Stream.of("hello", "world", "boy").reduce("", (a, b) -> b + "-" + a);
		assertThat(result).isEqualTo("boy-world-hello-");
	}

	@Test
	public void only_one_element() {
		String result = Stream.of("hello").reduce("1", (a, b) -> b + "-" + a);
		assertThat(result).isEqualTo("hello-1");
	}

	private String combineWithoutTrailingDash(String a, String b) {
		if (a.isEmpty()) {
			return b;
		}
		return b + "-" + a;
	}

	@Test
	public void givenStreamValues_whenReducedWithPrefixingMethodReference_thenHasNoTrailingDash() {
		String result = Stream.of("hello", "world").reduce("", this::combineWithoutTrailingDash);
		assertThat(result).isEqualTo("world-hello");
	}

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
