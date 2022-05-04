package zzk.study.java.core.lang.java8.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamSumUnitTest {

	public static class ArithmeticUtils {
		public static int add(int a, int b) {
			return a + b;
		}
	}

	public static class StreamSumCalculator {

		public static Integer getSumUsingCustomizedAccumulator(List<Integer> integers) {
			return integers.stream()
					.reduce(0, ArithmeticUtils::add);
		}

		public static Integer getSumUsingJavaAccumulator(List<Integer> integers) {
			return integers.stream()
					.reduce(0, Integer::sum);
		}

		//lambda
		public static Integer getSumUsingReduce(List<Integer> integers) {
			return integers.stream()
					.reduce(0, (a, b) -> a + b);
		}

		public static Integer getSumUsingCollect(List<Integer> integers) {
			return integers.stream()
					.collect(Collectors.summingInt(Integer::intValue));
		}

		public static Integer getSumUsingSum(List<Integer> integers) {
			return integers.stream()
					.mapToInt(Integer::intValue)
					.sum();
		}

		public static Integer getSumOfMapValues(Map<Object, Integer> map) {
			return map.values()
					.stream()
					.mapToInt(Integer::valueOf)
					.sum();
		}

		public static Integer getSumIntegersFromString(String str) {
			Integer sum = Arrays.stream(str.split(" "))
					.filter((s) -> s.matches("\\d+"))
					.mapToInt(Integer::valueOf)
					.sum();
			return sum;
		}
	}

	@Test
	public void givenListOfIntegersWhenSummingUsingCustomizedAccumulatorThenCorrectValueReturned() {
		List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
		Integer sum = StreamSumCalculator.getSumUsingCustomizedAccumulator(integers);
		assertEquals(15, sum.intValue());
	}

	private void assertEquals(int i, int intValue) {
	}

	@Test
	public void givenListOfIntegersWhenSummingUsingJavaAccumulatorThenCorrectValueReturned() {
		List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
		Integer sum = StreamSumCalculator.getSumUsingJavaAccumulator(integers);
		assertEquals(15, sum.intValue());
	}

	@Test
	public void givenListOfIntegersWhenSummingUsingReduceThenCorrectValueReturned() {
		List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
		Integer sum = StreamSumCalculator.getSumUsingReduce(integers);
		assertEquals(15, sum.intValue());
	}

	@Test
	public void givenListOfIntegersWhenSummingUsingCollectThenCorrectValueReturned() {
		List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
		Integer sum = StreamSumCalculator.getSumUsingCollect(integers);
		assertEquals(15, sum.intValue());
	}

	@Test
	public void givenListOfIntegersWhenSummingUsingSumThenCorrectValueReturned() {
		List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
		Integer sum = StreamSumCalculator.getSumUsingSum(integers);
		assertEquals(15, sum.intValue());
	}

	@Test
	public void givenListOfItemsWhenSummingUsingCustomizedAccumulatorThenCorrectValueReturned() {
		Item item1 = new Item(1, 10);
		Item item2 = new Item(2, 15);
		Item item3 = new Item(3, 25);
		Item item4 = new Item(4, 40);

		List<Item> items = Arrays.asList(item1, item2, item3, item4);

		Integer sum = StreamSumCalculatorWithObject.getSumUsingCustomizedAccumulator(items);
		assertEquals(90, sum.intValue());
	}

	@Test
	public void givenListOfItemsWhenSummingUsingJavaAccumulatorThenCorrectValueReturned() {
		Item item1 = new Item(1, 10);
		Item item2 = new Item(2, 15);
		Item item3 = new Item(3, 25);
		Item item4 = new Item(4, 40);

		List<Item> items = Arrays.asList(item1, item2, item3, item4);

		Integer sum = StreamSumCalculatorWithObject.getSumUsingJavaAccumulator(items);
		assertEquals(90, sum.intValue());
	}

	@Test
	public void givenListOfItemsWhenSummingUsingReduceThenCorrectValueReturned() {
		Item item1 = new Item(1, 10);
		Item item2 = new Item(2, 15);
		Item item3 = new Item(3, 25);
		Item item4 = new Item(4, 40);

		List<Item> items = Arrays.asList(item1, item2, item3, item4);

		Integer sum = StreamSumCalculatorWithObject.getSumUsingReduce(items);
		assertEquals(90, sum.intValue());
	}

	@Test
	public void givenListOfItemsWhenSummingUsingCollectThenCorrectValueReturned() {
		Item item1 = new Item(1, 10);
		Item item2 = new Item(2, 15);
		Item item3 = new Item(3, 25);
		Item item4 = new Item(4, 40);

		List<Item> items = Arrays.asList(item1, item2, item3, item4);

		Integer sum = StreamSumCalculatorWithObject.getSumUsingCollect(items);
		assertEquals(90, sum.intValue());
	}

	@Test
	public void givenListOfItemsWhenSummingUsingSumThenCorrectValueReturned() {
		Item item1 = new Item(1, 10);
		Item item2 = new Item(2, 15);
		Item item3 = new Item(3, 25);
		Item item4 = new Item(4, 40);

		List<Item> items = Arrays.asList(item1, item2, item3, item4);

		Integer sum = StreamSumCalculatorWithObject.getSumUsingSum(items);
		assertEquals(90, sum.intValue());
	}

	@Test
	public void givenMapWhenSummingThenCorrectValueReturned() {
		Map<Object, Integer> map = new HashMap<Object, Integer>();
		map.put(1, 10);
		map.put(2, 15);
		map.put(3, 25);
		map.put(4, 40);

		Integer sum = StreamSumCalculator.getSumOfMapValues(map);
		assertEquals(90, sum.intValue());
	}

	@Test
	public void givenStringWhenSummingThenCorrectValueReturned() {
		String string = "Item1 10 Item2 25 Item3 30 Item4 45";

		Integer sum = StreamSumCalculator.getSumIntegersFromString(string);
		assertEquals(110, sum.intValue());
	}

	public class Item {

		private int id;
		private Integer price;

		public Item(int id, Integer price) {
			super();
			this.id = id;
			this.price = price;
		}

		// Standard getters and setters
		public long getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Integer getPrice() {
			return price;
		}

		public void setPrice(Integer price) {
			this.price = price;
		}
	}

	public static class StreamSumCalculatorWithObject {

		public static Integer getSumUsingCustomizedAccumulator(List<Item> items) {
			return items.stream()
					.map(x -> x.getPrice())
					.reduce(0, ArithmeticUtils::add);
		}

		public static Integer getSumUsingJavaAccumulator(List<Item> items) {
			return items.stream()
					.map(x -> x.getPrice())
					.reduce(0, Integer::sum);
		}

		public static Integer getSumUsingReduce(List<Item> items) {
			return items.stream()
					.map(item -> item.getPrice())
					.reduce(0, (a, b) -> a + b);
		}

		public static Integer getSumUsingCollect(List<Item> items) {
			return items.stream()
					.map(x -> x.getPrice())
					.collect(Collectors.summingInt(Integer::intValue));
		}

		public static Integer getSumUsingSum(List<Item> items) {
			return items.stream()
					.mapToInt(x -> x.getPrice())
					.sum();
		}
	}
}
